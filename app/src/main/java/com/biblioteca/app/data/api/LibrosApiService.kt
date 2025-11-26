package com.biblioteca.app.data.api

import com.biblioteca.app.data.model.Libro
import retrofit2.http.GET

interface LibrosApiService {
    
    @GET("posts")
    suspend fun obtenerLibros(): List<Libro>
}
