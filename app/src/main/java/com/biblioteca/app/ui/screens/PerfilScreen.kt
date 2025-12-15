package com.biblioteca.app.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.biblioteca.app.data.model.Rol
import com.biblioteca.app.data.repository.PreferenciasRepository
import com.biblioteca.app.ui.viewmodel.PerfilViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onNavegar: (String) -> Unit
) {
    val contexto = LocalContext.current
    val viewModel = remember { PerfilViewModel(contexto) }
    val prefsRepo = remember { PreferenciasRepository(contexto) }
    val rolUsuario = prefsRepo.obtenerRol()
    
    val nombre by viewModel.nombre.collectAsState()
    val email by viewModel.email.collectAsState()
    val imagenUri by viewModel.imagenUri.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    
    var mostrarDialogo by remember { mutableStateOf(false) }
    var uriCamara by remember { mutableStateOf<Uri?>(null) }
    
    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.guardarImagen(it)
        }
    }
    
    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito && uriCamara != null) {
            viewModel.guardarImagen(uriCamara!!)
        }
    }
    
    val launcherPermisoCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { permitido ->
        if (permitido) {
            val archivo = File(contexto.cacheDir, "foto_${System.currentTimeMillis()}.jpg")
            uriCamara = FileProvider.getUriForFile(
                contexto,
                "${contexto.packageName}.provider",
                archivo
            )
            launcherCamara.launch(uriCamara)
        }
    }
    
    fun abrirCamara() {
        val tienePermiso = ContextCompat.checkSelfPermission(
            contexto,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        
        if (tienePermiso) {
            val archivo = File(contexto.cacheDir, "foto_${System.currentTimeMillis()}.jpg")
            uriCamara = FileProvider.getUriForFile(
                contexto,
                "${contexto.packageName}.provider",
                archivo
            )
            launcherCamara.launch(uriCamara)
        } else {
            launcherPermisoCamara.launch(Manifest.permission.CAMERA)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("perfil") },
                actions = {
                    IconButton(onClick = {
                        viewModel.cerrarSesion {
                            onNavegar("login")
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, "salir")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clickable { mostrarDialogo = true }
            ) {
                if (imagenUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imagenUri),
                        contentDescription = "foto",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "sin foto",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            
            TextButton(onClick = { mostrarDialogo = true }) {
                Text("cambiar foto")
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            if (cargando) {
                Text("cargando...")
            } else {
                Text("nombre", fontSize = 12.sp)
                Text(text = nombre, fontSize = 18.sp)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("correo", fontSize = 12.sp)
                Text(text = email, fontSize = 18.sp)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("rol", fontSize = 12.sp)
                Text(
                    text = when(rolUsuario) {
                        Rol.ADMIN -> "administrador"
                        Rol.EDITOR -> "editor"
                        Rol.LECTOR -> "lector"
                        Rol.INVITADO -> "invitado"
                    },
                    fontSize = 18.sp
                )
            }
            
            if (error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = error!!)
            }
        }
        
        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text("seleccionar imagen") },
                text = {
                    Column {
                        Button(
                            onClick = {
                                mostrarDialogo = false
                                launcherGaleria.launch("image/*")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("elegir de galeria")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                mostrarDialogo = false
                                abrirCamara()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("tomar foto")
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