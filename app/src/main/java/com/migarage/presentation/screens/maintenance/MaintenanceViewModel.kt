package com.migarage.presentation.screens.maintenance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migarage.domain.model.MaintenanceRecord
import com.migarage.domain.repository.MaintenanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MaintenanceViewModel @Inject constructor(
    repository: MaintenanceRepository
) : ViewModel() {
    val records: StateFlow<List<MaintenanceRecord>> = repository.getAllRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalSpent: StateFlow<Double> = repository.getAllRecords()
        .map { list -> list.mapNotNull { it.cost }.sum() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
}
