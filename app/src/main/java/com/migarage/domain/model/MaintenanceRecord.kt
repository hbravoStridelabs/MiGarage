package com.migarage.domain.model

import java.time.LocalDate

enum class ServiceType(val displayName: String) {
    OIL_CHANGE("Cambio de aceite"),
    AIR_FILTER("Filtro de aire"),
    OIL_FILTER("Filtro de aceite"),
    FUEL_FILTER("Filtro de combustible"),
    TIRES("Neumáticos"),
    BRAKES("Frenos"),
    BATTERY("Batería"),
    COOLANT("Refrigerante"),
    TRANSMISSION("Transmisión"),
    SUSPENSION("Suspensión"),
    ALIGNMENT("Alineación"),
    GENERAL_CHECK("Revisión general"),
    OTHER("Otro")
}

data class MaintenanceRecord(
    val id: String = java.util.UUID.randomUUID().toString(),
    val serviceType: ServiceType,
    val date: LocalDate,
    val mileage: Int,
    val workshop: String? = null,
    val cost: Double? = null,
    val notes: String? = null,
    val imagePath: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
