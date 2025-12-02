package com.biblioteca.app.data.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LibroTest : StringSpec({

    "el titulo debe ser correcto" {
        val libro = Libro(
            id = "1",
            titulo = "mi titulo",
            descripcion = "descripcion del libro",
            imagen = "https://ejemplo.com/imagen.jpg",
            precio = 15990.0
        )
        libro.titulo shouldBe "mi titulo"
    }

    "la descripcion debe ser correcta" {
        val libro = Libro(
            id = "1",
            titulo = "titulo",
            descripcion = "mi descripcion",
            imagen = "https://ejemplo.com/imagen.jpg",
            precio = 15990.0
        )
        libro.descripcion shouldBe "mi descripcion"
    }

    "el precio debe ser correcto" {
        val libro = Libro(
            id = "1",
            titulo = "titulo",
            descripcion = "descripcion",
            imagen = "https://ejemplo.com/imagen.jpg",
            precio = 12500.0
        )
        libro.precio shouldBe 12500.0
    }
})
