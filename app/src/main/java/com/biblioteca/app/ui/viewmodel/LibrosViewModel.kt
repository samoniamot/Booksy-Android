package com.biblioteca.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biblioteca.app.data.model.Libro
import com.biblioteca.app.data.repository.LibrosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class LibrosViewModel : ViewModel() {
    
    private val repositorio = LibrosRepository()
    
    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros
    
    private val _librosFiltrados = MutableStateFlow<List<Libro>>(emptyList())
    open val librosFiltrados: StateFlow<List<Libro>> = _librosFiltrados
    
    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda
    
    init {
        cargarLibros()
    }
    
    fun cargarLibros() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            try {
                val lista = repositorio.obtenerLibros()
                _libros.value = lista
                _librosFiltrados.value = lista
            } catch (e: Exception) {
                _error.value = "no se pudieron cargar los libros"
            } finally {
                _cargando.value = false
            }
        }
    }
    
    fun buscar(texto: String) {
        _busqueda.value = texto
        filtrar()
    }
    
    private fun filtrar() {
        val texto = _busqueda.value.lowercase()
        _librosFiltrados.value = if (texto.isEmpty()) {
            _libros.value
        } else {
            _libros.value.filter { libro ->
                libro.titulo.lowercase().contains(texto) ||
                libro.descripcion.lowercase().contains(texto)
            }
        }
    }
}