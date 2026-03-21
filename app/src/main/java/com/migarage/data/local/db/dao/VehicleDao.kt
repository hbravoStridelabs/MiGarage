package com.migarage.data.local.db.dao

import androidx.room.*
import com.migarage.data.local.db.entity.VehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle WHERE id = 'default' LIMIT 1")
    fun getVehicle(): Flow<VehicleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: VehicleEntity)

    @Query("UPDATE vehicle SET currentMileage = :mileage, updatedAt = :updatedAt WHERE id = 'default'")
    suspend fun updateMileage(mileage: Int, updatedAt: Long)
}
