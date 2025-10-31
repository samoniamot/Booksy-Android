package com.example.booksy.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booksy.data.local.EntidadLibro
import com.example.booksy.viewmodel.ModeloVistaLibros

@Composable
fun PantallaLibros(
    modeloVista: ModeloVistaLibros = viewModel(),
    onAgregarAlCarrito: (EntidadLibro) -> Unit,
    onNavegarPerfil: () -> Unit
) {
    val estadoLibros by modeloVista.estadoLibros.collectAsState()
    val filtro by modeloVista.filtro.collectAsState()
    val contadorCarrito by modeloVista.contadorCarrito.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Header con filtros y perfil
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Libros", style = MaterialTheme.typography.headlineMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Carrito: $contadorCarrito", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = onNavegarPerfil) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Person,
                        contentDescription = "Perfil"
                    )
                }
            }
        }

        // Filtros
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            listOf("todos", "ficcion", "no ficcion", "ciencia").forEach { categoria ->
                FilterChip(
                    selected = filtro == categoria,
                    onClick = { modeloVista.establecerFiltro(categoria) },
                    label = { Text(categoria) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        // Contenido
        when (val estado = estadoLibros) {
            is com.example.booksy.viewmodel.EstadoLibros.Cargando -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is com.example.booksy.viewmodel.EstadoLibros.Exito -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(estado.libros) { libro ->
                        TarjetaLibro(
                            libro = libro,
                            onAgregarAlCarrito = { onAgregarAlCarrito(libro) },
                            onMarcarLeido = { modeloVista.marcarComoLeido(libro.id) }
                        )
                    }
                }
            }
            is com.example.booksy.viewmodel.EstadoLibros.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${estado.mensaje}")
                }
            }
        }
    }
}

@Composable
fun TarjetaLibro(
    libro: EntidadLibro,
    onAgregarAlCarrito: () -> Unit,
    onMarcarLeido: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(libro.titulo, style = MaterialTheme.typography.titleMedium)
            Text(libro.autor, style = MaterialTheme.typography.bodyMedium)
            Text("$${libro.precio}", style = MaterialTheme.typography.bodyLarge)
            Text(libro.categoria, style = MaterialTheme.typography.bodySmall)
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                val scaleAgregar = remember { Animatable(1f) }
                val scaleLeido = remember { Animatable(1f) }
                Button(
                    onClick = {
                        onAgregarAlCarrito()
                        scaleAgregar.animateTo(0.9f)
                        scaleAgregar.animateTo(1f)
                    },
                    modifier = Modifier.scale(scaleAgregar.value)
                ) {
                    Text("Agregar")
                }
                Button(
                    onClick = {
                        onMarcarLeido()
                        scaleLeido.animateTo(0.9f)
                        scaleLeido.animateTo(1f)
                    },
                    modifier = Modifier.scale(scaleLeido.value)
                ) {
                    Text(if (libro.leido) "Leído" else "Marcar leído")
                }
            }
        }
    }
}
