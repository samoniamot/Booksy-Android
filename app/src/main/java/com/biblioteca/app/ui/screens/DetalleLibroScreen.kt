package com.biblioteca.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.biblioteca.app.data.api.LibrosRetrofitClient
import com.biblioteca.app.data.model.Libro
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLibroScreen(
    libroId: String,
    onVolver: () -> Unit
) {
    var libro by remember { mutableStateOf<Libro?>(null) }
    var cargando by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(libroId) {
        scope.launch {
            try {
                libro = LibrosRetrofitClient.servicioLibros.obtenerLibroPorId(libroId)
            } catch (e: Exception) {
                error = "error al cargar libro"
            }
            cargando = false
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(libro?.titulo ?: "Libro") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, "volver")
                    }
                }
            )
        }
    ) { padding ->
        if (cargando) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }
        } else if (libro != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // imagen del libro
                AsyncImage(
                    model = libro!!.imagen,
                    contentDescription = libro!!.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // titulo
                Text(
                    text = libro!!.titulo,
                    style = MaterialTheme.typography.headlineMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // categoria
                Text(
                    text = "Categoria: ${libro!!.categoria}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // precio
                Text(
                    text = "Precio: $${libro!!.precio.toInt()}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // descripcion
                Text(
                    text = "Descripcion",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = libro!!.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
