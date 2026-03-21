package com.migarage.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.migarage.domain.model.MaintenanceRecord
import com.migarage.domain.model.ServiceType
import java.time.LocalDate

@Entity(tableName = "maintenance_records")
data class MaintenanceRecordEntity(
    @PrimaryKey val id: String,
    val serviceType: String,
    val date: Long,
    val mileage: Int,
    val workshop: String?,
    val cost: Double?,
    val notes: String?,
    val imagePath: String?,
    val createdAt: Long
) {
    fun toDomain(): MaintenanceRecord = MaintenanceRecord(
        id = id,
        serviceType = ServiceType.valueOf(serviceType),
        date = LocalDate.ofEpochDay(date),
        mileage = mileage,
        workshop = workshop,
        cost = cost,
        notes = notes,
        imagePath = imagePath,
        createdAt = createdAt
    )

    companion object {
        fun fromDomain(record: MaintenanceRecord): MaintenanceRecordEntity = MaintenanceRecordEntity(
            id = record.id,
            serviceType = record.serviceType.name,
            date = record.date.toEpochDay(),
            mileage = record.mileage,
            workshop = record.workshop,
            cost = record.cost,
            notes = record.notes,
            imagePath = record.imagePath,
            createdAt = record.createdAt
        )
    }
}
