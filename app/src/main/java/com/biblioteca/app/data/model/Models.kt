package com.biblioteca.app.data.model

enum class Rol {
    ADMIN,
    EDITOR,
    LECTOR,
    INVITADO
}

data class Usuario(
    val id: Int,
    val email: String,
    val name: String?,
    val rol: Rol = Rol.LECTOR
)

data class Libro(
    val id: String? = null,
    val titulo: String,
    val descripcion: String,
    val imagen: String,
    val precio: Double
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
