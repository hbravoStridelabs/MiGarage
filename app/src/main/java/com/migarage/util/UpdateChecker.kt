package com.migarage.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.migarage.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object UpdateChecker {

    private const val REPO_OWNER = "hbravoStridelabs"
    private const val REPO_NAME = "MiGarage"
    private const val VERSION_URL = "https://api.github.com/repos/$REPO_OWNER/$REPO_NAME/releases/latest"
    private const val CHECK_INTERVAL_MS = 30 * 60 * 1000L // 30 minutes

    private var lastCheckTime = 0L
    private var hasShownUpdateDialogThisSession = false
    private var updateDismissedForVersion: String? = null

    fun checkForUpdates(context: Context, forceCheck: Boolean = false) {
        val currentVersion = BuildConfig.VERSION_NAME
        
        if (hasShownUpdateDialogThisSession && !forceCheck) {
            return
        }
        
        if (updateDismissedForVersion == currentVersion) {
            return
        }

        val currentTime = System.currentTimeMillis()
        if (!forceCheck && (currentTime - lastCheckTime) < CHECK_INTERVAL_MS) {
            return
        }

        lastCheckTime = currentTime

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(VERSION_URL)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Accept", "application/json")
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val json = JSONObject(response)

                    val tagName = json.optString("tag_name", "")
                    val versionName = tagName.removePrefix("v")
                    val downloadUrl = json.optJSONArray("assets")
                        ?.optJSONObject(0)
                        ?.optString("browser_download_url") ?: ""

                    if (isNewerVersion(versionName, currentVersion)) {
                        withContext(Dispatchers.Main) {
                            hasShownUpdateDialogThisSession = true
                            showUpdateDialog(context, UpdateInfo(versionName, downloadUrl))
                        }
                    }
                }
            } catch (e: Exception) {
                // Silent fail - don't bother user with network errors
            }
        }
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
        AlertDialog.Builder(context)
            .setTitle("Nueva versión disponible")
            .setMessage("La versión ${updateInfo.versionName} está disponible. ¿Deseas descargarla ahora?")
            .setPositiveButton("Descargar") { _: DialogInterface, _: Int ->
                downloadAndInstall(context, updateInfo.downloadUrl)
            }
            .setNegativeButton("Más tarde") { dialog: DialogInterface, _: Int ->
                updateDismissedForVersion = updateInfo.versionName
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun downloadAndInstall(context: Context, downloadUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    data class UpdateInfo(
        val versionName: String,
        val downloadUrl: String
    )
}
