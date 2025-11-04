package com.biblioteca.app.data.model

data class Usuario(
    val id: Int,
    val email: String,
    val nombre: String?
)

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String?,
    val categoria: String?
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
