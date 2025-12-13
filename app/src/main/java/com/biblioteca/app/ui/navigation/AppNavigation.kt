package com.biblioteca.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.biblioteca.app.ui.screens.LoginScreen
import com.biblioteca.app.ui.screens.PerfilScreen
import com.biblioteca.app.ui.screens.RegistroScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Rutas.Login.ruta
    ) {
        composable(Rutas.Login.ruta) {
            LoginScreen(
                onLoginExitoso = {
                    navController.navigate(Rutas.Perfil.ruta) {
                        popUpTo(Rutas.Login.ruta) { inclusive = true }
                    }
                },
                onIrARegistro = {
                    navController.navigate(Rutas.Registro.ruta)
                }
            )
        }
        
        composable(Rutas.Registro.ruta) {
            RegistroScreen(
                onRegistroExitoso = {
                    navController.navigate(Rutas.Perfil.ruta) {
                        popUpTo(Rutas.Login.ruta) { inclusive = true }
                    }
                },
                onVolverLogin = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Rutas.Perfil.ruta) {
            PerfilScreen(
                onCerrarSesion = {
                    navController.navigate(Rutas.Login.ruta) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
