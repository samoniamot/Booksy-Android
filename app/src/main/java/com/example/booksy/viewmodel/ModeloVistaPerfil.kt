package com.example.booksy.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksy.data.local.PreferenciasUsuario
import com.example.booksy.data.remote.ServicioApi
import com.example.booksy.data.remote.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModeloVistaPerfil @Inject constructor(
    private val servicioApi: ServicioApi,
    private val preferenciasUsuario: PreferenciasUsuario
) : ViewModel() {

    private val _estadoUsuario = MutableStateFlow<EstadoUsuario>(EstadoUsuario.Cargando)
    val estadoUsuario: StateFlow<EstadoUsuario> = _estadoUsuario

    private val _imagenPerfilUri = MutableStateFlow<Uri?>(null)
    val imagenPerfilUri: StateFlow<Uri?> = _imagenPerfilUri

    init {
        cargarImagenPerfilGuardada()
        cargarPerfilUsuario()
    }

    private fun cargarImagenPerfilGuardada() {
        viewModelScope.launch {
            preferenciasUsuario.imagenPerfilUri.collect { uriString ->
                uriString?.let {
                    _imagenPerfilUri.value = Uri.parse(it)
                }
            }
        }
    }

    fun cargarPerfilUsuario() {
        cargarImagenPerfilGuardada()
        cargarPerfilUsuario()
        cargarImagenPerfilGuardada()
        cargarPerfilUsuario()
    }

    fun cargarPerfilUsuario() {
        viewModelScope.launch {
            _estadoUsuario.value = EstadoUsuario.Cargando
            try {
                val token = preferenciasUsuario.obtenerTokenAuth()
                if (token != null) {
                    val respuesta = servicioApi.obtenerPerfil("Bearer $token")
                    if (respuesta.isSuccessful) {
                        respuesta.body()?.let { usuario ->
                            _estadoUsuario.value = EstadoUsuario.Exito(usuario)
                        } ?: run {
                            _estadoUsuario.value = EstadoUsuario.Error("Respuesta vacía del servidor")
                        }
                    } else {
                        _estadoUsuario.value = EstadoUsuario.Error("Error ${respuesta.code()}: ${respuesta.message()}")
                    }
                } else {
                    _estadoUsuario.value = EstadoUsuario.Error("No hay sesión activa")
                }
            } catch (e: Exception) {
                _estadoUsuario.value = EstadoUsuario.Error("Error de conexión: ${e.localizedMessage}")
            }
        }
    }

    fun seleccionarImagenDesdeGaleria(context: Context) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // Nota: En una implementación real, usarías un ActivityResultLauncher
        // Por ahora, solo mostramos que se puede hacer
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // Manejar error de permisos o app no disponible
        }
    }

    fun tomarFoto(context: Context) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Nota: En una implementación real, usarías un ActivityResultLauncher
        // Por ahora, solo mostramos que se puede hacer
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // Manejar error de permisos o app no disponible
        }
    }

    fun actualizarImagenPerfil(uri: Uri) {
        _imagenPerfilUri.value = uri
        // Aquí guardarías la URI en SharedPreferences o base de datos
        viewModelScope.launch {
            preferenciasUsuario.guardarImagenPerfil(uri.toString())
        }
    }
}

sealed class EstadoUsuario {
    object Cargando : EstadoUsuario()
    data class Exito(val usuario: Usuario) : EstadoUsuario()
    data class Error(val mensaje: String) : EstadoUsuario()
}
