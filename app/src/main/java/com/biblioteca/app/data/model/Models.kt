package com.biblioteca.app.data.model

data class Usuario(
    val id: Int,
    val email: String,
    val name: String?
)

data class SolicitudLogin(
    val email: String,
    val password: String
)

data class SolicitudRegistro(
    val email: String,
    val password: String,
    val name: String
)

data class RespuestaAuth(
    val authToken: String,
    val user: Usuario
)
