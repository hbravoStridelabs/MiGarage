package com.migarage.domain.model

import java.time.LocalDate

enum class DocumentType(val displayName: String, val abbreviation: String) {
    RT("Revisión Técnica", "RT"),
    SOAT("Seguro Obligatorio", "SOAT"),
    PC("Permiso de Circulación", "PC"),
    TP("Título de Propiedad", "TP"),
    CV("Compraventa", "CV"),
    RG("Revisión de Gases", "RG"),
    SV("Seguro Vehicular", "SV"),
    OTHER("Otro", "OTRO")
}

enum class DocumentStatus {
    ACTIVE,
    EXPIRING_SOON,
    EXPIRED,
    ARCHIVED
}

data class Document(
    val id: String = java.util.UUID.randomUUID().toString(),
    val type: DocumentType,
    val documentNumber: String,
    val issueDate: LocalDate,
    val expiryDate: LocalDate?,
    val status: DocumentStatus = DocumentStatus.ACTIVE,
    val imagePath: String,
    val extractedData: String? = null,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
