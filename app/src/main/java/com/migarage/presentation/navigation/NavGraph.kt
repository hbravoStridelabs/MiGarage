package com.migarage.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.migarage.presentation.screens.alerts.AlertsScreen
import com.migarage.presentation.screens.documents.AddDocumentScreen
import com.migarage.presentation.screens.documents.DocumentDetailScreen
import com.migarage.presentation.screens.documents.DocumentsScreen
import com.migarage.presentation.screens.home.HomeScreen
import com.migarage.presentation.screens.maintenance.AddMaintenanceScreen
import com.migarage.presentation.screens.maintenance.MaintenanceScreen
import com.migarage.presentation.screens.profile.EditVehicleScreen
import com.migarage.presentation.screens.profile.ProfileScreen
import com.migarage.presentation.screens.vehicle.AddVehicleScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDocuments = { navController.navigate(Screen.Documents.route) },
                onNavigateToMaintenance = { navController.navigate(Screen.Maintenance.route) },
                onNavigateToAddVehicle = { navController.navigate(Screen.AddVehicle.route) },
                onNavigateToVehicleDetail = { vehicleId ->
                    navController.navigate("vehicle/$vehicleId")
                }
            )
        }

        composable(
            route = "vehicle/{vehicleId}",
            arguments = listOf(navArgument("vehicleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
            EditVehicleScreen(
                vehicleId = vehicleId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddVehicle.route) {
            AddVehicleScreen(
                onBack = { navController.popBackStack() },
                onVehicleSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Documents.route) {
            DocumentsScreen(
                onDocumentClick = { id ->
                    navController.navigate("document/$id")
                },
                onAddClick = {
                    navController.navigate(Screen.AddDocument.route)
                }
            )
        }

        composable(
            route = Screen.DocumentDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DocumentDetailScreen(
                documentId = id,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddDocument.route) {
            AddDocumentScreen(
                onBack = { navController.popBackStack() },
                onDocumentSaved = { navController.popBackStack() }
            )
        }

        composable(Screen.Maintenance.route) {
            MaintenanceScreen(
                onAddClick = { navController.navigate(Screen.AddMaintenance.route) }
            )
        }

        composable(Screen.AddMaintenance.route) {
            AddMaintenanceScreen(
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }

        composable(Screen.Alerts.route) {
            AlertsScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onEditVehicle = { navController.navigate(Screen.EditVehicle.route) }
            )
        }

        composable(Screen.EditVehicle.route) {
            EditVehicleScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
