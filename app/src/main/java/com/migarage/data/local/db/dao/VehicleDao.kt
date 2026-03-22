package com.migarage.data.local.db.dao

import androidx.room.*
import com.migarage.data.local.db.entity.VehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle ORDER BY createdAt DESC")
    fun getAllVehicles(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicle WHERE id = :id LIMIT 1")
    fun getVehicleById(id: String): Flow<VehicleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: VehicleEntity)

    @Update
    suspend fun update(vehicle: VehicleEntity)

    @Query("UPDATE vehicle SET currentMileage = :mileage, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateMileage(id: String, mileage: Int, updatedAt: Long)

    @Query("DELETE FROM vehicle WHERE id = :id")
    suspend fun delete(id: String)
}
