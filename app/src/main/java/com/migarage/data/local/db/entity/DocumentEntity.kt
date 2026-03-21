package com.migarage.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.migarage.domain.model.Document
import com.migarage.domain.model.DocumentStatus
import com.migarage.domain.model.DocumentType
import java.time.LocalDate

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey val id: String,
    val type: String,
    val documentNumber: String,
    val issueDate: Long,
    val expiryDate: Long?,
    val status: String,
    val imagePath: String,
    val extractedData: String?,
    val notes: String?,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun toDomain(): Document = Document(
        id = id,
        type = DocumentType.valueOf(type),
        documentNumber = documentNumber,
        issueDate = LocalDate.ofEpochDay(issueDate),
        expiryDate = expiryDate?.let { LocalDate.ofEpochDay(it) },
        status = DocumentStatus.valueOf(status),
        imagePath = imagePath,
        extractedData = extractedData,
        notes = notes,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    companion object {
        fun fromDomain(doc: Document): DocumentEntity = DocumentEntity(
            id = doc.id,
            type = doc.type.name,
            documentNumber = doc.documentNumber,
            issueDate = doc.issueDate.toEpochDay(),
            expiryDate = doc.expiryDate?.toEpochDay(),
            status = doc.status.name,
            imagePath = doc.imagePath,
            extractedData = doc.extractedData,
            notes = doc.notes,
            createdAt = doc.createdAt,
            updatedAt = doc.updatedAt
        )
    }
}
