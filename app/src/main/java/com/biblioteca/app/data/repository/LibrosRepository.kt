package com.biblioteca.app.data.repository

import com.biblioteca.app.data.api.LibrosRetrofitClient
import com.biblioteca.app.data.model.Libro

class LibrosRepository {
    
    private val api = LibrosRetrofitClient.servicioLibros
    
    suspend fun obtenerLibros(): List<Libro> {
        return api.obtenerLibros()
    }
}
