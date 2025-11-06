package com.biblioteca.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biblioteca.app.data.api.RetrofitClient
import com.biblioteca.app.data.model.Libro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LibrosViewModel : ViewModel() {
    
    private val api = RetrofitClient.apiService
    
    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros
    
    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    init {
        cargarLibros()
    }
    
    fun cargarLibros() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            
            try {
                val respuesta = api.obtenerLibros()
                
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _libros.value = respuesta.body()!!
                } else {
                    _error.value = "no se pudieron cargar los libros"
                }
            } catch (e: Exception) {
                _error.value = "error de conexion con el servidor"
            } finally {
                _cargando.value = false
            }
        }
    }
}
