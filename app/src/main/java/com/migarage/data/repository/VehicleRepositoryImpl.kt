package com.migarage.data.repository

import com.migarage.data.local.db.dao.VehicleDao
import com.migarage.data.local.db.entity.VehicleEntity
import com.migarage.domain.model.Vehicle
import com.migarage.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepositoryImpl @Inject constructor(
    private val vehicleDao: VehicleDao
) : VehicleRepository {

    override fun getAllVehicles(): Flow<List<Vehicle>> =
        vehicleDao.getAllVehicles().map { list -> list.map { it.toDomain() } }

    override fun getVehicleById(id: String): Flow<Vehicle?> =
        vehicleDao.getVehicleById(id).map { it?.toDomain() }

    override suspend fun saveVehicle(vehicle: Vehicle) {
        vehicleDao.insert(VehicleEntity.fromDomain(vehicle))
    }

    override suspend fun updateVehicle(vehicle: Vehicle) {
        vehicleDao.update(VehicleEntity.fromDomain(vehicle))
    }

    override suspend fun updateMileage(id: String, mileage: Int) {
        vehicleDao.updateMileage(id, mileage, System.currentTimeMillis())
    }

    override suspend fun deleteVehicle(id: String) {
        vehicleDao.delete(id)
    }
}
