package com.biblioteca.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.biblioteca.app.data.model.Rol
import com.biblioteca.app.data.repository.PreferenciasRepository
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
    val contexto = LocalContext.current
    val prefsRepo = remember { PreferenciasRepository(contexto) }
    val rolUsuario = prefsRepo.obtenerRol()
    
    val puedeAgregar = rolUsuario == Rol.ADMIN || rolUsuario == Rol.EDITOR
    val puedeEditar = rolUsuario == Rol.ADMIN || rolUsuario == Rol.EDITOR
    val puedeEliminar = rolUsuario == Rol.ADMIN
    
    val librosFiltrados by viewModel.librosFiltrados.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    val busqueda by viewModel.busqueda.collectAsState()
    
    var libroAEliminar by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📚 Catalogo") },
                actions = {
                    IconButton(onClick = { onNavegar("perfil") }) {
                        Icon(Icons.Default.AccountCircle, "perfil")
                    }
                }
            )
        },
        floatingActionButton = {
            if (puedeAgregar) {
                FloatingActionButton(onClick = { onNavegar("agregar_libro") }) {
                    Icon(Icons.Default.Add, "agregar libro")
                }
            }
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
                label = { Text("Buscar libro") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                singleLine = true
            )
            
            // filtros de categoria ultra basicos
            val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
            val categorias = listOf("Todos", "Ficcion", "No Ficcion", "Ciencia", "Historia", "Arte")
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                categorias.forEach { cat ->
                    TextButton(
                        onClick = { viewModel.filtrarPorCategoria(cat) }
                    ) {
                        Text(
                            text = cat,
                            style = if (categoriaSeleccionada == cat) 
                                MaterialTheme.typography.labelLarge 
                            else 
                                MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
            
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
                    Text("no encontramos libros")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(librosFiltrados) { libro ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavegar("detalle_libro/${libro.id}") }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                AsyncImage(
                                    model = libro.imagen,
                                    contentDescription = libro.titulo,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = libro.titulo,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = libro.descripcion,
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = libro.categoria,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "$${libro.precio.toInt()}",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        if (puedeEditar) {
                                            IconButton(
                                                onClick = { onNavegar("editar_libro/${libro.id}") }
                                            ) {
                                                Icon(
                                                    Icons.Default.Edit,
                                                    "editar",
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                        if (puedeEliminar) {
                                            IconButton(
                                                onClick = { libroAEliminar = libro.id }
                                            ) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    "eliminar",
                                                    tint = MaterialTheme.colorScheme.error
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
    }
    
    if (libroAEliminar != null) {
        AlertDialog(
            onDismissRequest = { libroAEliminar = null },
            title = { Text("eliminar libro") },
            text = { Text("estas seguro de que quieres eliminar este libro?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.eliminarLibro(libroAEliminar!!)
                        libroAEliminar = null
                    }
                ) {
                    Text("si eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { libroAEliminar = null }) {
                    Text("cancelar")
                }
            }
        )
    }
}
