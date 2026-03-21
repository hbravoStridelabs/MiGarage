package com.migarage.domain.model

import java.time.LocalDate

enum class AlertType {
    DATE,
    MILEAGE,
    CUSTOM
}

data class Alert(
    val id: String = java.util.UUID.randomUUID().toString(),
    val type: AlertType,
    val title: String,
    val message: String,
    val dueDate: LocalDate? = null,
    val dueMileage: Int? = null,
    val relatedDocumentId: String? = null,
    val relatedMaintenanceId: String? = null,
    val isResolved: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
