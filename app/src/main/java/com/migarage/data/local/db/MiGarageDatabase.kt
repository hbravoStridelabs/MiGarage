package com.migarage.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.migarage.data.local.db.dao.AlertDao
import com.migarage.data.local.db.dao.DocumentDao
import com.migarage.data.local.db.dao.MaintenanceRecordDao
import com.migarage.data.local.db.dao.VehicleDao
import com.migarage.data.local.db.entity.AlertEntity
import com.migarage.data.local.db.entity.DocumentEntity
import com.migarage.data.local.db.entity.MaintenanceRecordEntity
import com.migarage.data.local.db.entity.VehicleEntity

@Database(
    entities = [
        DocumentEntity::class,
        MaintenanceRecordEntity::class,
        VehicleEntity::class,
        AlertEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MiGarageDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun maintenanceRecordDao(): MaintenanceRecordDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun alertDao(): AlertDao
}
