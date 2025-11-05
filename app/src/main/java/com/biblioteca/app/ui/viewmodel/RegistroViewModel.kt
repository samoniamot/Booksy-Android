package com.biblioteca.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biblioteca.app.data.repository.PreferenciasRepository
import com.biblioteca.app.data.model.SolicitudRegistro
import com.biblioteca.app.data.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistroViewModel(private val contexto: Context) : ViewModel() {
    private val apiService = RetrofitClient.apiService
    private val preferencias = PreferenciasRepository(contexto)
    
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre
    
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    
    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    fun actualizarNombre(valor: String) {
        _nombre.value = valor
        _error.value = null
    }
    
    fun actualizarEmail(valor: String) {
        _email.value = valor
        _error.value = null
    }
    
    fun actualizarPassword(valor: String) {
        _password.value = valor
        _error.value = null
    }
    
    fun registrar(onExito: () -> Unit) {
        if (_nombre.value.isEmpty() || _email.value.isEmpty() || _password.value.isEmpty()) {
            _error.value = "completa todo porfa"
            return
        }
        
        if (!_email.value.contains("@")) {
            _error.value = "el correo esta mal escrito"
            return
        }
        
        if (_password.value.length < 6) {
            _error.value = "la clave debe ser mas larga"
            return
        }
        
        _cargando.value = true
        _error.value = null
        
        viewModelScope.launch {
            try {
                val solicitud = SolicitudRegistro(
                    nombre = _nombre.value,
                    email = _email.value,
                    password = _password.value
                )
                
                val respuesta = apiService.registro(solicitud)
                
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    val token = respuesta.body()!!.authToken
                    preferencias.guardarToken(token)
                    onExito()
                } else {
                    _error.value = "el correo ya esta registrado"
                }
            } catch (e: Exception) {
                _error.value = "error de conexion"
            } finally {
                _cargando.value = false
            }
        }
    }
}
