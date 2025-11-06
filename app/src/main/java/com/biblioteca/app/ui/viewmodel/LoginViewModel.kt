package com.biblioteca.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biblioteca.app.data.api.RetrofitClient
import com.biblioteca.app.data.model.SolicitudLogin
import com.biblioteca.app.data.repository.PreferenciasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(context: Context) : ViewModel() {
    
    private val prefsRepo = PreferenciasRepository(context)
    private val api = RetrofitClient.apiService
    
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    
    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    fun actualizarEmail(nuevoEmail: String) {
        _email.value = nuevoEmail
    }
    
    fun actualizarPassword(nuevoPassword: String) {
        _password.value = nuevoPassword
    }
    
    fun login(onExito: () -> Unit) {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _error.value = "completa todos los campos"
            return
        }
        
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            
            try {
                val respuesta = api.login(
                    SolicitudLogin(_email.value, _password.value)
                )
                
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    val datos = respuesta.body()!!
                    prefsRepo.guardarToken(datos.authToken)
                    onExito()
                } else {
                    _error.value = "error al iniciar sesion"
                }
            } catch (e: Exception) {
                _error.value = "error de conexion"
            } finally {
                _cargando.value = false
            }
        }
    }
}
