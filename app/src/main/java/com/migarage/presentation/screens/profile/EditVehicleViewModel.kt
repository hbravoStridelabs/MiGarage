package com.migarage.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migarage.domain.model.Vehicle
import com.migarage.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditVehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {
    val vehicles: StateFlow<List<Vehicle>> = vehicleRepository.getAllVehicles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveVehicle(
        brand: String,
        model: String,
        year: Int,
        licensePlate: String,
        vin: String?,
        color: String?,
        mileage: Int
    ) {
        viewModelScope.launch {
            val newVehicle = Vehicle(
                brand = brand,
                model = model,
                year = year,
                licensePlate = licensePlate,
                vin = vin,
                color = color,
                currentMileage = mileage
            )
            vehicleRepository.saveVehicle(newVehicle)
        }
    }
}
