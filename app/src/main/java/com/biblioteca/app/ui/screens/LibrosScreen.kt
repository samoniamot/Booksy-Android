package com.biblioteca.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.biblioteca.app.ui.viewmodel.LibrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrosScreen(
    onNavegar: (String) -> Unit
) {
    val viewModel = remember { LibrosViewModel() }
    LibrosScreenConViewModel(viewModel = viewModel, onNavegar = onNavegar)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrosScreenConViewModel(
    viewModel: LibrosViewModel,
    onNavegar: (String) -> Unit = {}
) {
    val librosFiltrados by viewModel.librosFiltrados.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    val busqueda by viewModel.busqueda.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("mis libros") },
                actions = {
                    IconButton(onClick = { onNavegar("perfil") }) {
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
            OutlinedTextField(
                value = busqueda,
                onValueChange = { viewModel.buscar(it) },
                label = { Text("buscar por titulo") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true
            )
            
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
            } else if (librosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("no hay libros disponibles")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(librosFiltrados) { libro ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = libro.titulo,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = libro.contenido,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 3
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
