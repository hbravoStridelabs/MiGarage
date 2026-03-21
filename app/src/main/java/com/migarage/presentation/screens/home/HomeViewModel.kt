package com.migarage.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migarage.domain.model.Alert
import com.migarage.domain.model.Document
import com.migarage.domain.model.MaintenanceRecord
import com.migarage.domain.model.Vehicle
import com.migarage.domain.repository.AlertRepository
import com.migarage.domain.repository.DocumentRepository
import com.migarage.domain.repository.MaintenanceRepository
import com.migarage.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    vehicleRepository: VehicleRepository,
    documentRepository: DocumentRepository,
    maintenanceRepository: MaintenanceRepository,
    alertRepository: AlertRepository
) : ViewModel() {

    val vehicle: StateFlow<Vehicle?> = vehicleRepository.getVehicle()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val expiringDocuments: StateFlow<List<Document>> = documentRepository.getExpiringDocuments(30)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentMaintenance: StateFlow<List<MaintenanceRecord>> = maintenanceRepository.getRecentRecords(5)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeAlerts: StateFlow<List<Alert>> = alertRepository.getActiveAlerts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val documentCount: Int = 0
    val maintenanceCount: Int = 0
}
