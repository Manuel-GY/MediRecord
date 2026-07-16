package com.medirecord.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.medirecord.ui.screens.home.HomeScreen
import com.medirecord.ui.screens.add.AddMedicationScreen
import com.medirecord.ui.screens.detail.MedicationDetailScreen
import com.medirecord.ui.screens.history.HistoryScreen
import com.medirecord.ui.screens.settings.SettingsScreen

@Composable
fun MediRecordNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onAddMedication = { navController.navigate("add") },
                onMedicationClick = { id -> navController.navigate("detail/$id") },
                onHistoryClick = { navController.navigate("history") },
                onSettingsClick = { navController.navigate("settings") }
            )
        }

        composable("add") {
            AddMedicationScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            "detail/{medicationId}",
            arguments = listOf(navArgument("medicationId") { type = NavType.LongType })
        ) {
            MedicationDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("history") {
            HistoryScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
