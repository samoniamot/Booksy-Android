package com.biblioteca.app.ui.viewmodel

import com.biblioteca.app.data.model.Libro
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class LibrosViewModelTest : StringSpec({

    "la lista debe estar vacia al inicio antes de cargar" {
        val viewModel = LibrosViewModelFalso()
        viewModel.libros.value shouldBe emptyList()
    }

    "debe contener libros despues de cargar" {
        val librosFalsos = listOf(
            Libro(id = "1", titulo = "primer libro", descripcion = "descripcion del libro", imagen = "https://ejemplo.com/1.jpg", precio = 15990.0),
            Libro(id = "2", titulo = "segundo libro", descripcion = "contenido 2", imagen = "https://ejemplo.com/2.jpg", precio = 12500.0)
        )
        
        val viewModel = LibrosViewModelFalso()
        viewModel.simularCarga(librosFalsos)
        
        viewModel.libros.value shouldContainExactly librosFalsos
    }

    "el filtro debe funcionar por titulo" {
        val librosFalsos = listOf(
            Libro(id = "1", titulo = "kotlin basico", descripcion = "descripcion del libro", imagen = "https://ejemplo.com/1.jpg", precio = 15990.0),
            Libro(id = "2", titulo = "java avanzado", descripcion = "contenido 2", imagen = "https://ejemplo.com/2.jpg", precio = 12500.0),
            Libro(id = "3", titulo = "kotlin avanzado", descripcion = "contenido 3", imagen = "https://ejemplo.com/3.jpg", precio = 18990.0)
        )
        
        val viewModel = LibrosViewModelFalso()
        viewModel.simularCarga(librosFalsos)
        viewModel.buscar("kotlin")
        
        viewModel.librosFiltrados.value.size shouldBe 2
    }

    "busqueda vacia debe mostrar todos los libros" {
        val librosFalsos = listOf(
            Libro(id = "1", titulo = "android basics", descripcion = "contenido", imagen = "https://ejemplo.com/1.jpg", precio = 15990.0),
            Libro(id = "2", titulo = "compose guide", descripcion = "contenido", imagen = "https://ejemplo.com/2.jpg", precio = 12500.0)
        )
        
        val viewModel = LibrosViewModelFalso()
        viewModel.simularCarga(librosFalsos)
        viewModel.buscar("")
        
        viewModel.librosFiltrados.value.size shouldBe 2
    }
})

class LibrosViewModelFalso {
    
    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros = _libros
    
    private val _librosFiltrados = MutableStateFlow<List<Libro>>(emptyList())
    val librosFiltrados = _librosFiltrados
    
    private val _busqueda = MutableStateFlow("")
    
    fun simularCarga(lista: List<Libro>) {
        _libros.value = lista
        _librosFiltrados.value = lista
    }
    
    fun buscar(texto: String) {
        _busqueda.value = texto
        _librosFiltrados.value = if (texto.isEmpty()) {
            _libros.value
        } else {
            _libros.value.filter { it.titulo.lowercase().contains(texto.lowercase()) }
        }
    }
}
