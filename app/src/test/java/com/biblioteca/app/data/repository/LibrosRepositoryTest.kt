package com.biblioteca.app.data.repository

import com.biblioteca.app.data.api.LibrosApiService
import com.biblioteca.app.data.model.Libro
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class LibrosRepositoryTest : StringSpec({

    "obtenerLibros debe retornar lista de libros" {
        val librosFalsos = listOf(
            Libro(userId = 1, id = 1, title = "titulo 1", body = "cuerpo 1"),
            Libro(userId = 2, id = 2, title = "titulo 2", body = "cuerpo 2")
        )
        
        val apiMock = mockk<LibrosApiService>()
        coEvery { apiMock.obtenerLibros() } returns librosFalsos
        
        val repositorio = LibrosRepositoryTesteable(apiMock)
        
        runTest {
            val resultado = repositorio.obtenerLibros()
            resultado shouldContainExactly librosFalsos
        }
    }

    "obtenerLibros debe retornar lista vacia cuando no hay datos" {
        val apiMock = mockk<LibrosApiService>()
        coEvery { apiMock.obtenerLibros() } returns emptyList()
        
        val repositorio = LibrosRepositoryTesteable(apiMock)
        
        runTest {
            val resultado = repositorio.obtenerLibros()
            resultado shouldContainExactly emptyList()
        }
    }
})

class LibrosRepositoryTesteable(private val api: LibrosApiService) {
    suspend fun obtenerLibros(): List<Libro> {
        return api.obtenerLibros()
    }
}
