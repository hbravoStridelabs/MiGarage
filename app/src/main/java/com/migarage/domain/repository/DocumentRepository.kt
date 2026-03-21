package com.migarage.domain.repository

import com.migarage.domain.model.Document
import com.migarage.domain.model.DocumentStatus
import com.migarage.domain.model.DocumentType
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {
    fun getAllDocuments(): Flow<List<Document>>
    fun getDocumentsByType(type: DocumentType): Flow<List<Document>>
    fun getDocumentsByStatus(status: DocumentStatus): Flow<List<Document>>
    fun getExpiringDocuments(withinDays: Int): Flow<List<Document>>
    fun getDocumentById(id: String): Flow<Document?>
    suspend fun insertDocument(document: Document)
    suspend fun updateDocument(document: Document)
    suspend fun deleteDocument(id: String)
}
