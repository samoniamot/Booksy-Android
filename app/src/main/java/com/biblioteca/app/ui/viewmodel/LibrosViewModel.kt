package com.biblioteca.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.biblioteca.app.data.model.Libro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LibrosViewModel : ViewModel() {
    
    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros
    
    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    init {
        // El endpoint /books no existe en la API
        // Se muestra lista vacía como fallback
    }
}
