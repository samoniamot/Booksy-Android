package com.biblioteca.app.ui.navigation

sealed class Rutas(val ruta: String) {
    object Login : Rutas("login")
    object Registro : Rutas("regitsro")
    object Libros : Rutas("libros")
    object Perfil : Rutas("prefil")
}
