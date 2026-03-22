package com.migarage.domain.repository

import com.migarage.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    fun getAllVehicles(): Flow<List<Vehicle>>
    fun getVehicleById(id: String): Flow<Vehicle?>
    suspend fun saveVehicle(vehicle: Vehicle)
    suspend fun updateVehicle(vehicle: Vehicle)
    suspend fun updateMileage(id: String, mileage: Int)
    suspend fun deleteVehicle(id: String)
}
