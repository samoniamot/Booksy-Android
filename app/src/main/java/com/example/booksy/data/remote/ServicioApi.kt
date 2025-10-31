package com.example.booksy.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class SolicitudInicioSesion(val email: String, val password: String)
data class SolicitudRegistro(val email: String, val password: String, val name: String)
data class RespuestaAuth(val authToken: String, val user: Usuario)
data class Usuario(val id: String, val email: String, val name: String, val avatarUrl: String? = null)

data class Libro(
    val id: String,
    val titulo: String,
    val autor: String,
    val categoria: String,
    val precio: Double,
    val urlImagen: String? = null
)

interface ServicioApi {
    @POST("auth/login")
    suspend fun iniciarSesion(@Body solicitud: SolicitudInicioSesion): Response<RespuestaAuth>

    @POST("auth/signup")
    suspend fun registrarse(@Body solicitud: SolicitudRegistro): Response<RespuestaAuth>

    @GET("auth/me")
    suspend fun obtenerPerfil(@Header("Authorization") token: String): Response<Usuario>

    @GET("books")
    suspend fun obtenerLibros(@Header("Authorization") token: String? = null): Response<List<Libro>>
}