package com.biblioteca.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuperarContrasenaScreen(
    onVolver: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var enviado by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("recuperar contrasena") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, "volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            if (enviado) {
                Text("se envio un correo a $email con instrucciones para recuperar tu contrasena")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onVolver) {
                    Text("volver al login")
                }
            } else {
                Text("ingresa tu correo y te enviaremos instrucciones")
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it 
                        error = ""
                    },
                    label = { Text("correo electronico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = error.isNotEmpty()
                )
                
                if (error.isNotEmpty()) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        if (email.isEmpty()) {
                            error = "debes ingresar un correo"
                        } else if (!email.contains("@")) {
                            error = "el correo no es valido"
                        } else {
                            enviado = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("enviar correo")
                }
            }
        }
    }
}
