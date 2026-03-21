package com.migarage.domain.repository

import com.migarage.domain.model.Alert
import kotlinx.coroutines.flow.Flow

interface AlertRepository {
    fun getActiveAlerts(): Flow<List<Alert>>
    fun getResolvedAlerts(): Flow<List<Alert>>
    fun getAlertById(id: String): Flow<Alert?>
    suspend fun insertAlert(alert: Alert)
    suspend fun markAsResolved(id: String)
    suspend fun deleteAlert(id: String)
    suspend fun deleteResolvedAlerts()
}
