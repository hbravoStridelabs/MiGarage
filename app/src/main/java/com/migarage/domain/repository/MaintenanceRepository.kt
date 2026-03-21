package com.migarage.domain.repository

import com.migarage.domain.model.MaintenanceRecord
import com.migarage.domain.model.ServiceType
import kotlinx.coroutines.flow.Flow

interface MaintenanceRepository {
    fun getAllRecords(): Flow<List<MaintenanceRecord>>
    fun getRecordsByType(type: ServiceType): Flow<List<MaintenanceRecord>>
    fun getRecordById(id: String): Flow<MaintenanceRecord?>
    fun getRecentRecords(limit: Int): Flow<List<MaintenanceRecord>>
    suspend fun insertRecord(record: MaintenanceRecord)
    suspend fun updateRecord(record: MaintenanceRecord)
    suspend fun deleteRecord(id: String)
}
