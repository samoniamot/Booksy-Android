package com.example.booksy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksy.data.local.PreferenciasUsuario
import com.example.booksy.data.remote.ServicioApi
import com.example.booksy.data.remote.RespuestaAuth
import com.example.booksy.data.remote.SolicitudInicioSesion
import com.example.booksy.data.remote.SolicitudRegistro
import com.example.booksy.data.remote.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ModeloVistaAutenticacion @Inject constructor(
    private val servicioApi: ServicioApi,
    private val preferenciasUsuario: PreferenciasUsuario
) : ViewModel() {

    private val _estadoAutenticacion = MutableStateFlow<EstadoAutenticacion>(EstadoAutenticacion.Inactivo)
    val estadoAutenticacion: StateFlow<EstadoAutenticacion> = _estadoAutenticacion

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual

    init {
        verificarEstadoAuth()
    }

    private fun verificarEstadoAuth() {
        viewModelScope.launch {
            preferenciasUsuario.tokenAuth.collect { token ->
                if (token != null) {
                    cargarPerfilUsuario()
                } else {
                    _estadoAutenticacion.value = EstadoAutenticacion.Inactivo
                    _usuarioActual.value = null
                }
            }
        }
    }

    fun iniciarSesion(email: String, contrasena: String) {
        viewModelScope.launch {
            _estadoAutenticacion.value = EstadoAutenticacion.Cargando
            try {
                val respuesta = servicioApi.iniciarSesion(SolicitudInicioSesion(email, contrasena))
                if (respuesta.isSuccessful) {
                    respuesta.body()?.let { respuestaAuth ->
                        guardarDatosAuth(respuestaAuth)
                        _estadoAutenticacion.value = EstadoAutenticacion.Exito("Sesión iniciada correctamente")
                    }
                } else {
                    _estadoAutenticacion.value = EstadoAutenticacion.Error("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                _estadoAutenticacion.value = EstadoAutenticacion.Error("Error de conexión")
            }
        }
    }

    fun registrarse(email: String, contrasena: String, nombre: String) {
        viewModelScope.launch {
            _estadoAutenticacion.value = EstadoAutenticacion.Cargando
            try {
                val respuesta = servicioApi.registrarse(SolicitudRegistro(email, contrasena, nombre))
                if (respuesta.isSuccessful) {
                    respuesta.body()?.let { respuestaAuth ->
                        guardarDatosAuth(respuestaAuth)
                        _estadoAutenticacion.value = EstadoAutenticacion.Exito("Registro exitoso")
                    }
                } else {
                    _estadoAutenticacion.value = EstadoAutenticacion.Error("Error en el registro")
                }
            } catch (e: Exception) {
                _estadoAutenticacion.value = EstadoAutenticacion.Error("Error de conexión")
            }
        }
    }

    private suspend fun guardarDatosAuth(respuestaAuth: RespuestaAuth) {
        preferenciasUsuario.guardarDatosAuth(
            token = respuestaAuth.tokenAuth,
            idUsuario = respuestaAuth.usuario.id,
            email = respuestaAuth.usuario.email,
            nombre = respuestaAuth.usuario.name,
            avatar = respuestaAuth.usuario.avatarUrl
        )
        _usuarioActual.value = respuestaAuth.usuario
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            preferenciasUsuario.limpiarDatosAuth()
            _estadoAutenticacion.value = EstadoAutenticacion.Inactivo
            _usuarioActual.value = null
        }
    }

    private suspend fun cargarPerfilUsuario() {
        try {
            val respuesta = servicioApi.obtenerPerfil("")
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { usuario ->
                    _usuarioActual.value = usuario
                }
            }
        } catch (e: Exception) {
            // Manejar error silenciosamente
        }
    }
}

sealed class EstadoAutenticacion {
    object Inactivo : EstadoAutenticacion()
    object Cargando : EstadoAutenticacion()
    data class Exito(val mensaje: String) : EstadoAutenticacion()
    data class Error(val mensaje: String) : EstadoAutenticacion()
}