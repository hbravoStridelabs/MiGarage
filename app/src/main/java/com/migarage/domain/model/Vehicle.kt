package com.migarage.domain.model

import java.util.UUID

data class Vehicle(
    val id: String = UUID.randomUUID().toString(),
    val brand: String = "",
    val model: String = "",
    val year: Int = 0,
    val licensePlate: String = "",
    val vin: String? = null,
    val color: String? = null,
    val currentMileage: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    val displayName: String
        get() = if (brand.isNotBlank() && model.isNotBlank()) {
            "$brand $model"
        } else if (licensePlate.isNotBlank()) {
            licensePlate
        } else {
            "Mi Auto"
        }
}
