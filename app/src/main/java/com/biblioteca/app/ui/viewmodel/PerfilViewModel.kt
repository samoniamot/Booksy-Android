package com.biblioteca.app.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biblioteca.app.data.repository.PreferenciasRepository
import com.biblioteca.app.data.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(private val contexto: Context) : ViewModel() {
    private val apiService = RetrofitClient.apiService
    private val preferencias = PreferenciasRepository(contexto)
    
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre
    
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    
    private val _imagenUri = MutableStateFlow<String?>(null)
    val imagenUri: StateFlow<String?> = _imagenUri
    
    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    init {
        cargarPerfil()
        cargarImagenGuardada()
    }
    
    private fun cargarImagenGuardada() {
        val uriGuardado = preferencias.obtenerImagenPerfil()
        if (uriGuardado != null) {
            _imagenUri.value = uriGuardado
        }
    }
    
    fun cargarPerfil() {
        val token = preferencias.obtenerToken()
        if (token == null) {
            _error.value = "no hay sesion activa"
            return
        }
        
        _cargando.value = true
        _error.value = null
        
        viewModelScope.launch {
            try {
                val respuesta = apiService.obtenerPerfil("Bearer $token")
                
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    val usuario = respuesta.body()!!
                    _nombre.value = usuario.name ?: ""
                    _email.value = usuario.email
                } else {
                    _error.value = "error al cargar perfil"
                }
            } catch (e: Exception) {
                _error.value = "error de coneccion"
            } finally {
                _cargando.value = false
            }
        }
    }
    
    fun guardarImagen(uri: Uri) {
        _imagenUri.value = uri.toString()
        preferencias.guardarImagenPerfil(uri.toString())
    }
    
    fun cerrarSesion(onExito: () -> Unit) {
        preferencias.limpiar()
        onExito()
    }
}
