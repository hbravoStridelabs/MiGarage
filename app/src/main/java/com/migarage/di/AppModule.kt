package com.migarage.di

import android.content.Context
import androidx.room.Room
import com.migarage.data.local.db.MiGarageDatabase
import com.migarage.data.local.db.dao.AlertDao
import com.migarage.data.local.db.dao.DocumentDao
import com.migarage.data.local.db.dao.MaintenanceRecordDao
import com.migarage.data.local.db.dao.VehicleDao
import com.migarage.data.repository.AlertRepositoryImpl
import com.migarage.data.repository.DocumentRepositoryImpl
import com.migarage.data.repository.MaintenanceRepositoryImpl
import com.migarage.data.repository.VehicleRepositoryImpl
import com.migarage.domain.repository.AlertRepository
import com.migarage.domain.repository.DocumentRepository
import com.migarage.domain.repository.MaintenanceRepository
import com.migarage.domain.repository.VehicleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MiGarageDatabase {
        return Room.databaseBuilder(
            context,
            MiGarageDatabase::class.java,
            "migarage.db"
        ).build()
    }

    @Provides fun provideDocumentDao(db: MiGarageDatabase): DocumentDao = db.documentDao()
    @Provides fun provideMaintenanceRecordDao(db: MiGarageDatabase): MaintenanceRecordDao = db.maintenanceRecordDao()
    @Provides fun provideVehicleDao(db: MiGarageDatabase): VehicleDao = db.vehicleDao()
    @Provides fun provideAlertDao(db: MiGarageDatabase): AlertDao = db.alertDao()

    @Provides @Singleton
    fun provideDocumentRepository(impl: DocumentRepositoryImpl): DocumentRepository = impl
    @Provides @Singleton
    fun provideMaintenanceRepository(impl: MaintenanceRepositoryImpl): MaintenanceRepository = impl
    @Provides @Singleton
    fun provideVehicleRepository(impl: VehicleRepositoryImpl): VehicleRepository = impl
    @Provides @Singleton
    fun provideAlertRepository(impl: AlertRepositoryImpl): AlertRepository = impl
}
