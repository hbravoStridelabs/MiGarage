package com.migarage.domain.repository

import com.migarage.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    fun getVehicle(): Flow<Vehicle?>
    suspend fun saveVehicle(vehicle: Vehicle)
    suspend fun updateMileage(mileage: Int)
}
