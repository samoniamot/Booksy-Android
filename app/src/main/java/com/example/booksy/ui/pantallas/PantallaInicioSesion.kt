package com.example.booksy.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booksy.viewmodel.ModeloVistaAutenticacion

@Composable
fun PantallaInicioSesion(
    onInicioSesionExitoso: () -> Unit,
    onNavegarARegistro: () -> Unit,
    modeloVista: ModeloVistaAutenticacion = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorEmail by remember { mutableStateOf<String?>(null) }
    var errorContrasena by remember { mutableStateOf<String?>(null) }

    val estadoAuth by modeloVista.estadoAutenticacion.collectAsState()

    LaunchedEffect(estadoAuth) {
        if (estadoAuth is com.example.booksy.viewmodel.EstadoAutenticacion.Exito) {
            onInicioSesionExitoso()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorEmail = null
            },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = errorEmail != null,
            supportingText = errorEmail?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                errorContrasena = null
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = errorContrasena != null,
            supportingText = errorContrasena?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                errorEmail = if (email.isBlank()) "Email requerido" else null
                errorContrasena = if (contrasena.isBlank()) "Contraseña requerida" else null

                if (errorEmail == null && errorContrasena == null) {
                    modeloVista.inicarSesion(email, contrasena)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = estadoAuth !is com.example.booksy.viewmodel.EstadoAutenticacion.Cargando
        ) {
            if (estadoAuth is com.example.booksy.viewmodel.EstadoAutenticacion.Cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Iniciar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavegarARegistro) {
            Text("¿No tienes cuenta? Regístrate")
        }

        estadoAuth.let { estado ->
            when (estado) {
                is com.example.booksy.viewmodel.EstadoAutenticacion.Error -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = estado.mensaje,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is com.example.booksy.viewmodel.EstadoAutenticacion.Exito -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = estado.mensaje,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                else -> {}
            }
        }
    }
}