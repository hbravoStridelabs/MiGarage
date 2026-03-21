package com.migarage.data.repository

import com.migarage.data.local.db.dao.MaintenanceRecordDao
import com.migarage.data.local.db.entity.MaintenanceRecordEntity
import com.migarage.domain.model.MaintenanceRecord
import com.migarage.domain.model.ServiceType
import com.migarage.domain.repository.MaintenanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaintenanceRepositoryImpl @Inject constructor(
    private val maintenanceRecordDao: MaintenanceRecordDao
) : MaintenanceRepository {

    override fun getAllRecords(): Flow<List<MaintenanceRecord>> =
        maintenanceRecordDao.getAllRecords().map { list -> list.map { it.toDomain() } }

    override fun getRecordsByType(type: ServiceType): Flow<List<MaintenanceRecord>> =
        maintenanceRecordDao.getRecordsByType(type.name).map { list -> list.map { it.toDomain() } }

    override fun getRecordById(id: String): Flow<MaintenanceRecord?> =
        maintenanceRecordDao.getRecordById(id).map { it?.toDomain() }

    override fun getRecentRecords(limit: Int): Flow<List<MaintenanceRecord>> =
        maintenanceRecordDao.getRecentRecords(limit).map { list -> list.map { it.toDomain() } }

    override suspend fun insertRecord(record: MaintenanceRecord) {
        maintenanceRecordDao.insert(MaintenanceRecordEntity.fromDomain(record))
    }

    override suspend fun updateRecord(record: MaintenanceRecord) {
        maintenanceRecordDao.update(MaintenanceRecordEntity.fromDomain(record))
    }

    override suspend fun deleteRecord(id: String) {
        maintenanceRecordDao.delete(id)
    }
}
