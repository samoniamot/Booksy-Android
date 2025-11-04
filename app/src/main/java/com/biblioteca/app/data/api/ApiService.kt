package com.biblioteca.app.data.api

import com.biblioteca.app.data.model.RespuestaAuth
import com.biblioteca.app.data.model.SolicitudLogin
import com.biblioteca.app.data.model.SolicitudRegistro
import com.biblioteca.app.data.model.Usuario
import com.biblioteca.app.data.model.Libro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    
    @POST("auth/login")
    suspend fun login(@Body solicitud: SolicitudLogin): Response<RespuestaAuth>
    
    @POST("auth/signup")
    suspend fun registro(@Body solicitud: SolicitudRegistro): Response<RespuestaAuth>
    
    @GET("auth/me")
    suspend fun obtenerPerfil(@Header("Authorization") token: String): Response<Usuario>
    
    @GET("books")
    suspend fun obtenerLibros(): Response<List<Libro>>
}
