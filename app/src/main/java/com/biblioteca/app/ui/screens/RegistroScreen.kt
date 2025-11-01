package com.biblioteca.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.biblioteca.app.ui.viewmodel.RegistroViewModel

@Composable
fun RegistroScreen(
    onNavegar: (String) -> Unit
) {
    val contexto = LocalContext.current
    val viewModel = remember { RegistroViewModel(contexto) }
    
    val nombre by viewModel.nombre.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "crear cuenta",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        OutlinedTextField(
            value = nombre,
            onValueChange = { viewModel.actualizarNombre(it) },
            label = { Text("nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.actualizarEmail(it) },
            label = { Text("correo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.actualizarPassword(it) },
            label = { Text("contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        
        AnimatedVisibility(visible = error != null) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                viewModel.registrar {
                    onNavegar("prefil")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !cargando
        ) {
            if (cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("registrarse")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(onClick = { onNavegar("login") }) {
            Text("ya tienes cuenta? inicia sesion")
        }
    }
}
