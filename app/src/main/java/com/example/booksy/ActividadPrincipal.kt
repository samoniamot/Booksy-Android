package com.example.booksy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booksy.data.local.EntidadLibro
import com.example.booksy.ui.pantallas.*
import com.example.booksy.ui.theme.BooksyTheme
import com.example.booksy.viewmodel.ModeloVistaLibros
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActividadPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavegacion()
                }
            }
        }
    }
}

@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    val modeloVistaLibros: ModeloVistaLibros = viewModel()
    
    NavHost(
        navController = navController, 
        startDestination = "login",
        enterTransition = { 
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = { 
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        composable("login") {
            PantallaInicioSesion(
                onNavegarRegistro = { navController.navigate("registro") },
                onLoginExitoso = { navController.navigate("libros") }
            )
        }
        composable("registro") {
            PantallaRegistro(
                onNavegarLogin = { navController.navigate("login") },
                onRegistroExitoso = { navController.navigate("libros") }
            )
        }
        composable("libros") {
            PantallaLibros(
                modeloVista = modeloVistaLibros,
                onAgregarAlCarrito = { libro ->
                    modeloVistaLibros.agregarAlCarrito()
                },
                onNavegarPerfil = { navController.navigate("perfil") }
            )
        }
        composable("perfil") {
            PantallaPerfil(
                onNavegarLibros = { navController.navigate("libros") }
            )
        }
    }
}
