package com.biblioteca.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.biblioteca.app.ui.screens.LibrosScreen
import com.biblioteca.app.ui.screens.LoginScreen
import com.biblioteca.app.ui.screens.PerfilScreen
import com.biblioteca.app.ui.screens.RegistroScreen

@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onNavegar = { ruta ->
                    navController.navigate(ruta) {
                        if (ruta == "libros") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }
        
        composable("regitsro") {
            RegistroScreen(
                onNavegar = { ruta ->
                    navController.navigate(ruta) {
                        if (ruta == "libros") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }
        
        composable("libros") {
            LibrosScreen(
                onNavegar = { ruta ->
                    navController.navigate(ruta)
                }
            )
        }
        
        composable("perfil") {
            PerfilScreen(
                onNavegar = { ruta ->
                    if (ruta == "login") {
                        navController.navigate(ruta) {
                            popUpTo(0) { inclusive = true }
                        }
                    } else {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}
