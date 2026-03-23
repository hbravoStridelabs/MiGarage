package com.migarage.util

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.migarage.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object UpdateChecker {

    private const val REPO_OWNER = "hbravoStridelabs"
    private const val REPO_NAME = "MiGarage"
    private const val TAGS_URL = "https://api.github.com/repos/$REPO_OWNER/$REPO_NAME/tags"
    private const val CHECK_INTERVAL_MS = 30 * 60 * 1000L
    private const val PREFS_NAME = "migarage_update_prefs"
    private const val KEY_DISMISSED_VERSION = "dismissed_version"
    private const val KEY_LAST_CHECK = "last_check_time"

    private var pendingDownloadContext: Context? = null
    private var pendingDownloadReceiver: BroadcastReceiver? = null

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun checkForUpdates(context: Context, forceCheck: Boolean = false) {
        val currentVersion = BuildConfig.VERSION_NAME
        val prefs = getPrefs(context)

        val dismissedVersion = prefs.getString(KEY_DISMISSED_VERSION, null)
        if (dismissedVersion == currentVersion) {
            return
        }

        val currentTime = System.currentTimeMillis()
        val lastCheck = prefs.getLong(KEY_LAST_CHECK, 0)
        if (!forceCheck && (currentTime - lastCheck) < CHECK_INTERVAL_MS) {
            return
        }

        prefs.edit().putLong(KEY_LAST_CHECK, currentTime).apply()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val connection = URL(TAGS_URL).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Accept", "application/json")
                connection.setRequestProperty("User-Agent", "MiGarage-Android")
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val latestTag = parseLatestTag(response)

                    if (latestTag.isNullOrEmpty()) {
                        return@launch
                    }

                    if (!isNewerVersion(latestTag, currentVersion)) {
                        return@launch
                    }

                    val releaseUrl = "https://api.github.com/repos/$REPO_OWNER/$REPO_NAME/releases/tags/$latestTag"
                    val releaseConnection = URL(releaseUrl).openConnection() as HttpURLConnection
                    releaseConnection.requestMethod = "GET"
                    releaseConnection.setRequestProperty("Accept", "application/json")
                    releaseConnection.setRequestProperty("User-Agent", "MiGarage-Android")
                    releaseConnection.connectTimeout = 10000
                    releaseConnection.readTimeout = 10000

                    var downloadUrl = ""
                    if (releaseConnection.responseCode == HttpURLConnection.HTTP_OK) {
                        val releaseResponse = releaseConnection.inputStream.bufferedReader().use { it.readText() }
                        downloadUrl = parseDownloadUrl(releaseResponse)
                    }

                    if (downloadUrl.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            showUpdateDialog(context, UpdateInfo(latestTag, downloadUrl))
                        }
                    }
                }
            } catch (e: Exception) {
                // Silent fail - network errors are not critical
            }
        }
    }

    private fun parseLatestTag(tagsResponse: String): String? {
        val versionTags = mutableListOf<String>()
        val regex = Regex(""""name":\s*"v?(\d+\.\d+\.\d+)"""")
        val matches = regex.findAll(tagsResponse)
        for (match in matches) {
            versionTags.add(match.groupValues[1])
        }

        if (versionTags.isEmpty()) return null
        return versionTags.maxWithOrNull { a, b -> compareVersions(a, b) }
    }

    private fun parseDownloadUrl(releaseResponse: String): String {
        return try {
            val jsonArray = JSONArray(releaseResponse)
            for (i in 0 until jsonArray.length()) {
                val release = jsonArray.getJSONObject(i)
                val assets = release.optJSONArray("assets") ?: continue
                for (j in 0 until assets.length()) {
                    val asset = assets.getJSONObject(j)
                    val name = asset.optString("name", "")
                    if (name.endsWith(".apk", ignoreCase = true)) {
                        return asset.optString("browser_download_url", "")
                    }
                }
            }
            ""
        } catch (e: Exception) {
            ""
        }
    }

    private fun compareVersions(a: String, b: String): Int {
        val aParts = a.split(".").map { it.toIntOrNull() ?: 0 }
        val bParts = b.split(".").map { it.toIntOrNull() ?: 0 }
        for (i in 0 until maxOf(aParts.size, bParts.size)) {
            val aPart = aParts.getOrElse(i) { 0 }
            val bPart = bParts.getOrElse(i) { 0 }
            if (aPart > bPart) return 1
            if (aPart < bPart) return -1
        }
        return 0
    }

    private fun isNewerVersion(newVersion: String, currentVersion: String): Boolean {
        if (newVersion.isEmpty()) return false
        val newParts = newVersion.split(".").map { it.toIntOrNull() ?: 0 }
        val currentParts = currentVersion.split(".").map { it.toIntOrNull() ?: 0 }
        for (i in 0 until maxOf(newParts.size, currentParts.size)) {
            val newPart = newParts.getOrElse(i) { 0 }
            val currentPart = currentParts.getOrElse(i) { 0 }
            if (newPart > currentPart) return true
            if (newPart < currentPart) return false
        }
        return false
    }

    fun showUpdateDialog(context: Context, updateInfo: UpdateInfo) {
        val prefs = getPrefs(context)
        val currentDismissed = prefs.getString(KEY_DISMISSED_VERSION, null)

        if (currentDismissed == updateInfo.versionName) {
            return
        }

        AlertDialog.Builder(context)
            .setTitle("Nueva versión disponible")
            .setMessage("La versión ${updateInfo.versionName} está disponible. ¿Deseas descargarla ahora?")
            .setPositiveButton("Descargar") { _: DialogInterface, _: Int ->
                downloadAndInstall(context, updateInfo.downloadUrl, updateInfo.versionName)
            }
            .setNegativeButton("Más tarde") { dialog: DialogInterface, _: Int ->
                prefs.edit().putString(KEY_DISMISSED_VERSION, updateInfo.versionName).apply()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun downloadAndInstall(context: Context, downloadUrl: String, versionName: String) {
        pendingDownloadContext = context.applicationContext

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                val action = intent.action
                if (action == "com.migarage.UPDATE_DOWNLOAD_COMPLETE") {
                    val success = intent.getBooleanExtra("success", false)
                    ctx.unregisterReceiver(this)
                    pendingDownloadContext = null
                    pendingDownloadReceiver = null

                    if (success) {
                        val apkFile = intent.getParcelableExtra<Uri>("apk_uri")
                        if (apkFile != null) {
                            installApk(ctx, apkFile)
                        }
                    } else {
                        Toast.makeText(ctx, "Descarga fallida. Abre GitHub para actualizar.", Toast.LENGTH_LONG).show()
                        openGitHubReleases(ctx)
                    }
                }
            }
        }

        pendingDownloadReceiver = receiver
        context.registerReceiver(receiver, IntentFilter("com.migarage.UPDATE_DOWNLOAD_COMPLETE"))

        CoroutineScope(Dispatchers.IO).launch {
            downloadApk(context, downloadUrl, versionName)
        }
    }

    private suspend fun downloadApk(context: Context, downloadUrl: String, versionName: String) {
        var inputStream: java.io.InputStream? = null
        var outputStream: FileOutputStream? = null
        var connection: HttpURLConnection? = null

        try {
            connection = URL(downloadUrl).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "MiGarage-Android")
            connection.connectTimeout = 30000
            connection.readTimeout = 30000
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                sendResult(context, false)
                return
            }

            val downloadsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                ?: throw Exception("No se pudo acceder al directorio de descargas")

            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs()
            }

            val apkFile = File(downloadsDir, "MiGarage_$versionName.apk")

            val contentLength = connection.contentLength
            inputStream = connection.inputStream

            outputStream = FileOutputStream(apkFile)
            val buffer = ByteArray(8192)
            var bytesRead: Int
            var totalBytesRead = 0L

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
                totalBytesRead += bytesRead
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()
            connection.disconnect()

            val apkUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                apkFile
            )

            sendResult(context, true, apkUri)

        } catch (e: Exception) {
            e.printStackTrace()
            sendResult(context, false)
        } finally {
            try { inputStream?.close() } catch (e: Exception) {}
            try { outputStream?.close() } catch (e: Exception) {}
            try { connection?.disconnect() } catch (e: Exception) {}
        }
    }

    private fun sendResult(context: Context, success: Boolean, apkUri: Uri? = null) {
        try {
            val intent = Intent("com.migarage.UPDATE_DOWNLOAD_COMPLETE").apply {
                putExtra("success", success)
                apkUri?.let { putExtra("apk_uri", it) }
            }
            context.sendBroadcast(intent)
        } catch (e: Exception) {
            // Context might be invalid after app shutdown
        }
    }

    private fun installApk(context: Context, apkUri: Uri) {
        try {
            val installIntent = Intent(Intent.ACTION_VIEW).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                setDataAndType(apkUri, "application/vnd.android.package-archive")
            }

            if (installIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(installIntent)
            } else {
                Toast.makeText(context, "No se pudo iniciar la instalación.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error al instalar. Abre GitHub para actualizar.", Toast.LENGTH_LONG).show()
            openGitHubReleases(context)
        }
    }

    private fun openGitHubReleases(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/$REPO_OWNER/$REPO_NAME/releases"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No se pudo abrir el navegador.", Toast.LENGTH_SHORT).show()
        }
    }

    fun cleanup(context: Context) {
        pendingDownloadReceiver?.let {
            try {
                context.unregisterReceiver(it)
            } catch (e: Exception) {}
        }
        pendingDownloadReceiver = null
        pendingDownloadContext = null
    }

    data class UpdateInfo(
        val versionName: String,
        val downloadUrl: String
    )
}