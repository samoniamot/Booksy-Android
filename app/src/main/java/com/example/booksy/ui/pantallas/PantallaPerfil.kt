package com.example.booksy.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.booksy.R
import com.example.booksy.viewmodel.ModeloVistaPerfil

@Composable
fun PantallaPerfil(
    modeloVista: ModeloVistaPerfil = viewModel(),
    onNavegarLibros: () -> Unit
) {
    val context = LocalContext.current
    val estadoUsuario by modeloVista.estadoUsuario.collectAsState()
    val imagenPerfilUri by modeloVista.imagenPerfilUri.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Perfil", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = onNavegarLibros) {
                Text("Volver a Libros")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Imagen de perfil
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            if (imagenPerfilUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imagenPerfilUri),
                    contentDescription = "Imagen de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Imagen de perfil por defecto",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones para cambiar imagen
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { modeloVista.seleccionarImagenDesdeGaleria(context) }) {
                Text("Galería")
            }
            Button(onClick = { modeloVista.tomarFoto(context) }) {
                Text("Cámara")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Información del usuario
        when (val estado = estadoUsuario) {
            is com.example.booksy.viewmodel.EstadoUsuario.Cargando -> {
                CircularProgressIndicator()
            }
            is com.example.booksy.viewmodel.EstadoUsuario.Exito -> {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nombre: ${estado.usuario.nombre ?: "No especificado"}")
                        Text("Email: ${estado.usuario.email}")
                        Text("ID: ${estado.usuario.id}")
                    }
                }
            }
            is com.example.booksy.viewmodel.EstadoUsuario.Error -> {
                Text("Error al cargar perfil: ${estado.mensaje}")
            }
        }
    }
}
