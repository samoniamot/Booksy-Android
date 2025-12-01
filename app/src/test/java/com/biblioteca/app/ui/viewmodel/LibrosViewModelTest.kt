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
            Libro(userId = 1, id = 1, title = "primer libro", body = "contenido 1"),
            Libro(userId = 2, id = 2, title = "segundo libro", body = "contenido 2")
        )
        
        val viewModel = LibrosViewModelFalso()
        viewModel.simularCarga(librosFalsos)
        
        viewModel.libros.value shouldContainExactly librosFalsos
    }

    "el filtro debe funcionar por titulo" {
        val librosFalsos = listOf(
            Libro(userId = 1, id = 1, title = "kotlin basico", body = "contenido 1"),
            Libro(userId = 2, id = 2, title = "java avanzado", body = "contenido 2"),
            Libro(userId = 3, id = 3, title = "kotlin avanzado", body = "contenido 3")
        )
        
        val viewModel = LibrosViewModelFalso()
        viewModel.simularCarga(librosFalsos)
        viewModel.buscar("kotlin")
        
        viewModel.librosFiltrados.value.size shouldBe 2
    }

    "busqueda vacia debe mostrar todos los libros" {
        val librosFalsos = listOf(
            Libro(userId = 1, id = 1, title = "libro uno", body = "contenido"),
            Libro(userId = 2, id = 2, title = "libro dos", body = "contenido")
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
            _libros.value.filter { it.title.lowercase().contains(texto.lowercase()) }
        }
    }
}
