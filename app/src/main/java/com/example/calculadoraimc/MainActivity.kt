package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calculadoraimc.feature.detail.view.DetailScreen
import com.example.calculadoraimc.feature.detail.viewmodel.DetailViewModelFactory
import com.example.calculadoraimc.feature.help.view.HelpScreen
import com.example.calculadoraimc.feature.history.view.HistoryScreen
import com.example.calculadoraimc.feature.history.viewmodel.HistoryViewModelFactory
import com.example.calculadoraimc.feature.home.view.Home
import com.example.calculadoraimc.feature.home.viewmodel.HomeViewModelFactory
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by rememberSaveable {
                mutableStateOf(false)
            }

            CalculadoraIMCTheme(darkTheme = isDarkTheme) {
                AppNavigation(
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    onToggleTheme: () -> Unit
) {
    val navController = rememberNavController()
    val application = navController.context.applicationContext as IMCApplication
    val repository = application.repository

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home(
                viewModel = viewModel(factory = HomeViewModelFactory(repository)),
                onNavigateToHistory = { navController.navigate("history") },
                onNavigateToHelp = { navController.navigate("help") },
                onToggleTheme = onToggleTheme
            )
        }
        composable("history") {
            HistoryScreen(
                viewModel = viewModel(factory = HistoryViewModelFactory(repository)),
                onBack = { navController.popBackStack() },
                onItemClick = { historyId ->
                    navController.navigate("detail/$historyId")
                }
            )
        }
        composable(
            route = "detail/{historyId}",
            arguments = listOf(navArgument("historyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val historyId = backStackEntry.arguments?.getInt("historyId") ?: 0
            DetailScreen(
                viewModel = viewModel(factory = DetailViewModelFactory(repository, historyId)),
                onBack = { navController.popBackStack() }
            )
        }
        composable("help") {
            HelpScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
