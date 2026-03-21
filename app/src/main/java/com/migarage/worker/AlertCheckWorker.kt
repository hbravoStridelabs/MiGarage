package com.migarage.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.migarage.R
import com.migarage.domain.repository.AlertRepository
import com.migarage.domain.repository.DocumentRepository
import com.migarage.presentation.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class AlertCheckWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val documentRepository: DocumentRepository,
    private val alertRepository: AlertRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        createNotificationChannel()

        val expiringDocs = documentRepository.getExpiringDocuments(30).first()
        val activeAlerts = alertRepository.getActiveAlerts().first()

        if (expiringDocs.isNotEmpty()) {
            val doc = expiringDocs.first()
            val daysUntil = java.time.temporal.ChronoUnit.DAYS.between(
                java.time.LocalDate.now(),
                doc.expiryDate
            ).toInt()

            if (daysUntil in 0..7) {
                showNotification(
                    title = "Documento por vencer",
                    message = "${doc.type.displayName} vence en $daysUntil dias",
                    id = 1
                )
            }
        }

        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Alertas MiGarage",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones de documentos y mantenimientos"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, message: String, id: Int) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }

    companion object {
        const val CHANNEL_ID = "migarage_alerts"
        const val WORK_NAME = "alert_check_work"
    }
}
