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
fun PantallaRegistro(
    onRegistroExitoso: () -> Unit,
    onNavegarAInicioSesion: () -> Unit,
    modeloVista: ModeloVistaAutenticacion = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorEmail by remember { mutableStateOf<String?>(null) }
    var errorContrasena by remember { mutableStateOf<String?>(null) }
    var errorConfirmarContrasena by remember { mutableStateOf<String?>(null) }

    val estadoAuth by modeloVista.estadoAutenticacion.collectAsState()

    LaunchedEffect(estadoAuth) {
        if (estadoAuth is com.example.booksy.viewmodel.EstadoAutenticacion.Exito) {
            onRegistroExitoso()
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
            text = "Registro",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                errorNombre = null
            },
            label = { Text("Nombre") },
            isError = errorNombre != null,
            supportingText = errorNombre?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmarContrasena,
            onValueChange = {
                confirmarContrasena = it
                errorConfirmarContrasena = null
            },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = errorConfirmarContrasena != null,
            supportingText = errorConfirmarContrasena?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                errorNombre = if (nombre.isBlank()) "Nombre requerido" else null
                errorEmail = if (email.isBlank()) "Email requerido" else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email inválido" else null
                errorContrasena = if (contrasena.length < 6) "Contraseña debe tener al menos 6 caracteres" else null
                errorConfirmarContrasena = if (confirmarContrasena != contrasena) "Las contraseñas no coinciden" else null

                if (errorNombre == null && errorEmail == null && errorContrasena == null && errorConfirmarContrasena == null) {
                    modeloVista.registrarse(email, contrasena, nombre)
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
                Text("Registrarse")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavegarAInicioSesion) {
            Text("¿Ya tienes cuenta? Inicia sesión")
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