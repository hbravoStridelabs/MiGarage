package com.migarage.data.repository

import com.migarage.data.local.db.dao.AlertDao
import com.migarage.data.local.db.entity.AlertEntity
import com.migarage.domain.model.Alert
import com.migarage.domain.repository.AlertRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlertRepositoryImpl @Inject constructor(
    private val alertDao: AlertDao
) : AlertRepository {

    override fun getActiveAlerts(): Flow<List<Alert>> =
        alertDao.getActiveAlerts().map { list -> list.map { it.toDomain() } }

    override fun getResolvedAlerts(): Flow<List<Alert>> =
        alertDao.getResolvedAlerts().map { list -> list.map { it.toDomain() } }

    override fun getAlertById(id: String): Flow<Alert?> =
        alertDao.getAlertById(id).map { it?.toDomain() }

    override suspend fun insertAlert(alert: Alert) {
        alertDao.insert(AlertEntity.fromDomain(alert))
    }

    override suspend fun markAsResolved(id: String) {
        alertDao.markAsResolved(id)
    }

    override suspend fun deleteAlert(id: String) {
        alertDao.delete(id)
    }

    override suspend fun deleteResolvedAlerts() {
        alertDao.deleteResolvedAlerts()
    }
}
