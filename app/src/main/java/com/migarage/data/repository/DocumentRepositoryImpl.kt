package com.migarage.data.repository

import com.migarage.data.local.db.dao.DocumentDao
import com.migarage.data.local.db.entity.DocumentEntity
import com.migarage.domain.model.Document
import com.migarage.domain.model.DocumentStatus
import com.migarage.domain.model.DocumentType
import com.migarage.domain.repository.DocumentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentRepositoryImpl @Inject constructor(
    private val documentDao: DocumentDao
) : DocumentRepository {

    override fun getAllDocuments(): Flow<List<Document>> =
        documentDao.getAllDocuments().map { list -> list.map { it.toDomain() } }

    override fun getDocumentsByType(type: DocumentType): Flow<List<Document>> =
        documentDao.getDocumentsByType(type.name).map { list -> list.map { it.toDomain() } }

    override fun getDocumentsByStatus(status: DocumentStatus): Flow<List<Document>> =
        documentDao.getDocumentsByStatus(status.name).map { list -> list.map { it.toDomain() } }

    override fun getExpiringDocuments(withinDays: Int): Flow<List<Document>> {
        val maxDate = LocalDate.now().plusDays(withinDays.toLong()).toEpochDay()
        return documentDao.getExpiringDocuments(maxDate).map { list -> list.map { it.toDomain() } }
    }

    override fun getDocumentById(id: String): Flow<Document?> =
        documentDao.getDocumentById(id).map { it?.toDomain() }

    override suspend fun insertDocument(document: Document) {
        documentDao.insert(DocumentEntity.fromDomain(document))
    }

    override suspend fun updateDocument(document: Document) {
        documentDao.update(DocumentEntity.fromDomain(document.copy(updatedAt = System.currentTimeMillis())))
    }

    override suspend fun deleteDocument(id: String) {
        documentDao.delete(id)
    }
}
