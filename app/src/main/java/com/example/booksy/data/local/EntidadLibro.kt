package com.example.booksy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros")
data class EntidadLibro(
    @PrimaryKey val id: String,
    val titulo: String,
    val autor: String,
    val categoria: String,
    val precio: Double,
    val urlImagen: String? = null,
    val leido: Boolean = false,
    val libroUsuario: Boolean = false
)