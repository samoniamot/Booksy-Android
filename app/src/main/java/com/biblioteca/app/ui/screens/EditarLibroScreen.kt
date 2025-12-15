package com.biblioteca.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.biblioteca.app.data.api.LibrosRetrofitClient
import com.biblioteca.app.data.model.Libro
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarLibroScreen(
    libroId: String,
    onVolver: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(true) }
    var guardando by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }
    
    var errorTitulo by remember { mutableStateOf("") }
    var errorDescripcion by remember { mutableStateOf("") }
    var errorPrecio by remember { mutableStateOf("") }
    
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(libroId) {
        try {
            val libro = LibrosRetrofitClient.servicioLibros.obtenerLibroPorId(libroId)
            titulo = libro.titulo
            descripcion = libro.descripcion
            precio = libro.precio.toString()
            imagen = libro.imagen
        } catch (e: Exception) {
            mensaje = "error al cargar libro"
        } finally {
            cargando = false
        }
    }
    
    fun validar(): Boolean {
        var valido = true
        
        if (titulo.isEmpty()) {
            errorTitulo = "el titulo es obligatorio"
            valido = false
        } else {
            errorTitulo = ""
        }
        
        if (descripcion.isEmpty()) {
            errorDescripcion = "la descripcion es obligatoria"
            valido = false
        } else {
            errorDescripcion = ""
        }
        
        if (precio.isEmpty()) {
            errorPrecio = "el precio es obligatorio"
            valido = false
        } else {
            val precioNum = precio.toDoubleOrNull()
            if (precioNum == null || precioNum <= 0) {
                errorPrecio = "el precio debe ser un numero mayor a 0"
                valido = false
            } else {
                errorPrecio = ""
            }
        }
        
        return valido
    }
    
    fun guardar() {
        if (!validar()) return
        
        guardando = true
        scope.launch {
            try {
                val libro = Libro(
                    id = libroId,
                    titulo = titulo,
                    descripcion = descripcion,
                    precio = precio.toDouble(),
                    imagen = imagen.ifEmpty { "" }
                )
                LibrosRetrofitClient.servicioLibros.actualizarLibro(libroId, libro)
                mensaje = "libro actualizado"
                onVolver()
            } catch (e: Exception) {
                mensaje = "error al actualizar"
            } finally {
                guardando = false
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("editar libro") },
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
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { 
                        titulo = it
                        errorTitulo = ""
                    },
                    label = { Text("titulo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = errorTitulo.isNotEmpty()
                )
                if (errorTitulo.isNotEmpty()) {
                    Text(errorTitulo, color = MaterialTheme.colorScheme.error)
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { 
                        descripcion = it
                        errorDescripcion = ""
                    },
                    label = { Text("descripcion") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    isError = errorDescripcion.isNotEmpty()
                )
                if (errorDescripcion.isNotEmpty()) {
                    Text(errorDescripcion, color = MaterialTheme.colorScheme.error)
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = precio,
                    onValueChange = { 
                        precio = it
                        errorPrecio = ""
                    },
                    label = { Text("precio") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = errorPrecio.isNotEmpty()
                )
                if (errorPrecio.isNotEmpty()) {
                    Text(errorPrecio, color = MaterialTheme.colorScheme.error)
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = imagen,
                    onValueChange = { imagen = it },
                    label = { Text("url de imagen (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                if (mensaje.isNotEmpty()) {
                    Text(mensaje)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                Button(
                    onClick = { guardar() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !guardando
                ) {
                    if (guardando) {
                        Text("guardando...")
                    } else {
                        Text("guardar cambios")
                    }
                }
            }
        }
    }
}
