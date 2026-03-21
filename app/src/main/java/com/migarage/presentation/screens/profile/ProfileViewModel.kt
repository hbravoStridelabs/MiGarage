package com.migarage.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migarage.domain.model.Vehicle
import com.migarage.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    repository: VehicleRepository
) : ViewModel() {
    val vehicle: StateFlow<Vehicle?> = repository.getVehicle()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
