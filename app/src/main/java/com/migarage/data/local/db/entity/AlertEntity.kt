package com.migarage.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.migarage.domain.model.Alert
import com.migarage.domain.model.AlertType
import java.time.LocalDate

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey val id: String,
    val type: String,
    val title: String,
    val message: String,
    val dueDate: Long?,
    val dueMileage: Int?,
    val relatedDocumentId: String?,
    val relatedMaintenanceId: String?,
    val isResolved: Boolean,
    val createdAt: Long
) {
    fun toDomain(): Alert = Alert(
        id = id,
        type = AlertType.valueOf(type),
        title = title,
        message = message,
        dueDate = dueDate?.let { LocalDate.ofEpochDay(it) },
        dueMileage = dueMileage,
        relatedDocumentId = relatedDocumentId,
        relatedMaintenanceId = relatedMaintenanceId,
        isResolved = isResolved,
        createdAt = createdAt
    )

    companion object {
        fun fromDomain(alert: Alert): AlertEntity = AlertEntity(
            id = alert.id,
            type = alert.type.name,
            title = alert.title,
            message = alert.message,
            dueDate = alert.dueDate?.toEpochDay(),
            dueMileage = alert.dueMileage,
            relatedDocumentId = alert.relatedDocumentId,
            relatedMaintenanceId = alert.relatedMaintenanceId,
            isResolved = alert.isResolved,
            createdAt = alert.createdAt
        )
    }
}
