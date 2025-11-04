package com.biblioteca.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.biblioteca.app.ui.viewmodel.LibrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrosScreen(
    onNavegar: (String) -> Unit
) {
    val contexto = LocalContext.current
    val viewModel = remember { LibrosViewModel(contexto) }
    
    val libros by viewModel.libros.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("mis libros") },
                actions = {
                    IconButton(onClick = { onNavegar("prefil") }) {
                        Icon(Icons.Default.AccountCircle, "perfil")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (cargando) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = error!!,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.cargarLibros() }) {
                            Text("reintentar")
                        }
                    }
                }
            } else if (libros.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("no hay libros disponibles")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(libros) { libro ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = libro.titulo,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 2
                                )
                                
                                Column {
                                    if (libro.autor != null) {
                                        Text(
                                            text = libro.autor,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    if (libro.categoria != null) {
                                        Text(
                                            text = libro.categoria,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
