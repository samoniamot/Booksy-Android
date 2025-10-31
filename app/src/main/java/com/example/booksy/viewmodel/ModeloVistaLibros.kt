package com.example.booksy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksy.data.local.AppDatabase
import com.example.booksy.data.local.EntidadLibro
import com.example.booksy.data.remote.ServicioApi
import com.example.booksy.data.remote.Libro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ModeloVistaLibros @Inject constructor(
    private val servicioApi: ServicioApi,
    private val baseDatos: AppDatabase
) : ViewModel() {

    private val _estadoLibros = MutableStateFlow<EstadoLibros>(EstadoLibros.Cargando)
    val estadoLibros: StateFlow<EstadoLibros> = _estadoLibros

    private val _filtro = MutableStateFlow("todos")
    val filtro: StateFlow<String> = _filtro

    private val _contadorCarrito = MutableStateFlow(0)
    val contadorCarrito: StateFlow<Int> = _contadorCarrito

    init {
        cargarLibros()
    }

    fun cargarLibros() {
        viewModelScope.launch {
            _estadoLibros.value = EstadoLibros.Cargando
            try {
                val respuesta = servicioApi.obtenerLibros()
                if (respuesta.isSuccessful) {
                    respuesta.body()?.let { librosApi ->
                        val entidades = librosApi.map { it.aEntidad() }
                        baseDatos.libroDao().insertarLibros(entidades)
                    }
                }

                val librosLocales = baseDatos.libroDao().obtenerTodosLibros()
                combine(librosLocales, _filtro) { libros, filt ->
                    if (filt == "todos") libros else libros.filter { it.categoria == filt }
                }.collect { librosFiltrados ->
                    _estadoLibros.value = EstadoLibros.Exito(librosFiltrados)
                }
            } catch (e: Exception) {
                val librosLocales = baseDatos.libroDao().obtenerTodosLibros()
                combine(librosLocales, _filtro) { libros, filt ->
                    if (filt == "todos") libros else libros.filter { it.categoria == filt }
                }.collect { librosFiltrados ->
                    _estadoLibros.value = EstadoLibros.Exito(librosFiltrados)
                }
            }
        }
    }

    fun establecerFiltro(nuevoFiltro: String) {
        _filtro.value = nuevoFiltro
    }

    fun agregarAlCarrito() {
        _contadorCarrito.value += 1
    }

    fun marcarComoLeido(idLibro: String) {
        viewModelScope.launch {
            val libro = baseDatos.libroDao().obtenerTodosLibros().value?.find { it.id == idLibro }
            libro?.let {
                val actualizado = it.copy(leido = !it.leido)
                baseDatos.libroDao().actualizarLibro(actualizado)
            }
        }
    }

    private fun Libro.aEntidad() = EntidadLibro(
        id = id,
        titulo = titulo,
        autor = autor,
        categoria = categoria,
        precio = precio,
        urlImagen = urlImagen
    )
}

sealed class EstadoLibros {
    object Cargando : EstadoLibros()
    data class Exito(val libros: List<EntidadLibro>) : EstadoLibros()
    data class Error(val mensaje: String) : EstadoLibros()
}