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

    override fun getVehicle(): Flow<Vehicle?> =
        vehicleDao.getVehicle().map { it?.toDomain() }

    override suspend fun saveVehicle(vehicle: Vehicle) {
        vehicleDao.insert(VehicleEntity.fromDomain(vehicle))
    }

    override suspend fun updateMileage(mileage: Int) {
        vehicleDao.updateMileage(mileage, System.currentTimeMillis())
    }
}
