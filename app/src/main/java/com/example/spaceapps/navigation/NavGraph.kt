package com.example.spaceapps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.spaceapps.ui.pages.Home
import com.example.spaceapps.ui.pages.Login
import com.example.spaceapps.ui.pages.SpaceRocket
import com.example.spaceapps.ui.pages.Splash

/**
 * Rutas de navegación de la aplicación
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object RocketDetail : Screen("rocket_detail/{rocketId}") {
        fun createRoute(rocketId: String) = "rocket_detail/$rocketId"
    }
}

/**
 * Configuración del grafo de navegación
 */
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Pantalla de Splash
        composable(Screen.Splash.route) {
            Splash(
                onSplashFinished = {
                    // Navegar a Login y remover Splash del stack
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla de Login
        composable(Screen.Login.route) {
            Login(
                onLoginSuccess = {
                    // Navegar a Home y remover Login del stack
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla de Home
        composable(Screen.Home.route) {
            Home(
                onRocketClick = { rocketId ->
                    // Navegar a detalle del cohete
                    navController.navigate(Screen.RocketDetail.createRoute(rocketId))
                },
                onLogout = {
                    // Navegar a Login y limpiar el stack
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla de Detalle del Cohete
        composable(
            route = Screen.RocketDetail.route,
            arguments = listOf(
                navArgument("rocketId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rocketId = backStackEntry.arguments?.getString("rocketId") ?: ""
            SpaceRocket(
                rocketId = rocketId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

