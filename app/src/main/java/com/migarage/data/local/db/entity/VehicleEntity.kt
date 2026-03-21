package com.migarage.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.migarage.domain.model.Vehicle

@Entity(tableName = "vehicle")
data class VehicleEntity(
    @PrimaryKey val id: String,
    val brand: String,
    val model: String,
    val year: Int,
    val licensePlate: String,
    val vin: String?,
    val color: String?,
    val currentMileage: Int,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun toDomain(): Vehicle = Vehicle(
        id = id,
        brand = brand,
        model = model,
        year = year,
        licensePlate = licensePlate,
        vin = vin,
        color = color,
        currentMileage = currentMileage,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    companion object {
        fun fromDomain(vehicle: Vehicle): VehicleEntity = VehicleEntity(
            id = vehicle.id,
            brand = vehicle.brand,
            model = vehicle.model,
            year = vehicle.year,
            licensePlate = vehicle.licensePlate,
            vin = vehicle.vin,
            color = vehicle.color,
            currentMileage = vehicle.currentMileage,
            createdAt = vehicle.createdAt,
            updatedAt = vehicle.updatedAt
        )
    }
}
