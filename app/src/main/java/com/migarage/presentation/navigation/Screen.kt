package com.migarage.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Description
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    data object Home : Screen("home", "Inicio", Icons.Default.Home)
    data object Documents : Screen("documents", "Docs", Icons.Default.Description)
    data object Maintenance : Screen("maintenance", "Taller", Icons.Default.Build)
    data object Alerts : Screen("alerts", "Alertas", Icons.Default.Notifications)
    data object Profile : Screen("profile", "Perfil", Icons.Default.Person)

    data object DocumentDetail : Screen("document/{id}", "Detalle Documento")
    data object AddDocument : Screen("add_document", "Agregar Documento")
    data object AddMaintenance : Screen("add_maintenance", "Agregar Servicio")
    data object AddVehicle : Screen("add_vehicle", "Agregar Vehículo")
    data object EditVehicle : Screen("edit_vehicle", "Editar Vehiculo")
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Documents,
    Screen.Maintenance,
    Screen.Alerts,
    Screen.Profile
)
