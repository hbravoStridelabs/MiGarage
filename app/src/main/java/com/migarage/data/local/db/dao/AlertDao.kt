package com.migarage.data.local.db.dao

import androidx.room.*
import com.migarage.data.local.db.entity.AlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Query("SELECT * FROM alerts WHERE isResolved = 0 ORDER BY dueDate ASC, createdAt DESC")
    fun getActiveAlerts(): Flow<List<AlertEntity>>

    @Query("SELECT * FROM alerts WHERE isResolved = 1 ORDER BY createdAt DESC")
    fun getResolvedAlerts(): Flow<List<AlertEntity>>

    @Query("SELECT * FROM alerts WHERE id = :id")
    fun getAlertById(id: String): Flow<AlertEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alert: AlertEntity)

    @Query("UPDATE alerts SET isResolved = 1 WHERE id = :id")
    suspend fun markAsResolved(id: String)

    @Query("DELETE FROM alerts WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM alerts WHERE isResolved = 1")
    suspend fun deleteResolvedAlerts()
}
