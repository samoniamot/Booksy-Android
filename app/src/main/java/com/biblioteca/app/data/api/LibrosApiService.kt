package com.biblioteca.app.data.api

import com.biblioteca.app.data.model.Libro
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LibrosApiService {
    
    @GET("api/libros")
    suspend fun obtenerLibros(): List<Libro>
    
    @GET("api/libros/{id}")
    suspend fun obtenerLibroPorId(@Path("id") id: String): Libro
    
    @POST("api/libros")
    suspend fun crearLibro(@Body libro: Libro): Libro
    
    @PUT("api/libros/{id}")
    suspend fun actualizarLibro(@Path("id") id: String, @Body libro: Libro): Libro
    
    @DELETE("api/libros/{id}")
    suspend fun eliminarLibro(@Path("id") id: String)
}
