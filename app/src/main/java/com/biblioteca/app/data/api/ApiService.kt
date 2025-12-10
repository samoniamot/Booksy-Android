package com.biblioteca.app.data.api

import com.biblioteca.app.data.model.RespuestaAuth
import com.biblioteca.app.data.model.SolicitudLogin
import com.biblioteca.app.data.model.SolicitudRegistro
import com.biblioteca.app.data.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    
    @POST("auth/login")
    suspend fun login(@Body solicitud: SolicitudLogin): Response<RespuestaAuth>
    
    @POST("auth/signup")
    suspend fun registro(@Body solicitud: SolicitudRegistro): Response<RespuestaAuth>
    
    @GET("auth/me")
    suspend fun obtenerPerfil(): Response<Usuario>
}
