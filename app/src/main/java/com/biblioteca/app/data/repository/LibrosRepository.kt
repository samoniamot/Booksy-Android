package com.biblioteca.app.data.repository

import com.biblioteca.app.data.api.LibrosRetrofitClient
import com.biblioteca.app.data.model.Libro

class LibrosRepository {
    
    private val api = LibrosRetrofitClient.servicioLibros
    
    suspend fun obtenerLibros(): List<Libro> {
        return api.obtenerLibros()
    }
    
    suspend fun obtenerLibroPorId(id: String): Libro {
        return api.obtenerLibroPorId(id)
    }
    
    suspend fun crearLibro(libro: Libro): Libro {
        return api.crearLibro(libro)
    }
    
    suspend fun actualizarLibro(id: String, libro: Libro): Libro {
        return api.actualizarLibro(id, libro)
    }
    
    suspend fun eliminarLibro(id: String) {
        api.eliminarLibro(id)
    }
}
