package com.migarage.data.local.db.dao

import androidx.room.*
import com.migarage.data.local.db.entity.MaintenanceRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceRecordDao {
    @Query("SELECT * FROM maintenance_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<MaintenanceRecordEntity>>

    @Query("SELECT * FROM maintenance_records WHERE serviceType = :type ORDER BY date DESC")
    fun getRecordsByType(type: String): Flow<List<MaintenanceRecordEntity>>

    @Query("SELECT * FROM maintenance_records WHERE id = :id")
    fun getRecordById(id: String): Flow<MaintenanceRecordEntity?>

    @Query("SELECT * FROM maintenance_records ORDER BY date DESC LIMIT :limit")
    fun getRecentRecords(limit: Int): Flow<List<MaintenanceRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: MaintenanceRecordEntity)

    @Update
    suspend fun update(record: MaintenanceRecordEntity)

    @Query("DELETE FROM maintenance_records WHERE id = :id")
    suspend fun delete(id: String)
}
