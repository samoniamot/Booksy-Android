package com.biblioteca.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.biblioteca.app.ui.viewmodel.PerfilViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onNavegar: (String) -> Unit
) {
    val contexto = LocalContext.current
    val viewModel = remember { PerfilViewModel(contexto) }
    
    val nombre by viewModel.nombre.collectAsState()
    val email by viewModel.email.collectAsState()
    val imagenUri by viewModel.imagenUri.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    
    var mostrarDialogo by remember { mutableStateOf(false) }
    
    val camaraPermiso = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )
    
    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        // imagen capturada
    }
    
    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.guardarImagen(it)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("mi perfil") },
                actions = {
                    IconButton(onClick = {
                        viewModel.cerrarSesion {
                            onNavegar("login")
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, "cerrar sesion")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // foto de perfil
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clickable { mostrarDialogo = true }
            ) {
                if (imagenUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imagenUri),
                        contentDescription = "foto perfil",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "sin foto",
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            TextButton(onClick = { mostrarDialogo = true }) {
                Text("cambiar foto")
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            if (cargando) {
                CircularProgressIndicator()
            } else {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "nombre",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = nombre,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "correo electronico",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
            
            if (error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        // dialogo para elegir camara o galeria
        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text("seleccionar foto") },
                text = {
                    Column {
                        TextButton(
                            onClick = {
                                mostrarDialogo = false
                                if (camaraPermiso.hasPermission) {
                                    // abrir camara
                                } else {
                                    camaraPermiso.launchPermissionRequest()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("tomar foto")
                        }
                        
                        TextButton(
                            onClick = {
                                mostrarDialogo = false
                                launcherGaleria.launch("image/*")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("elegir de galeria")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("cancelar")
                    }
                }
            )
        }
    }
}
