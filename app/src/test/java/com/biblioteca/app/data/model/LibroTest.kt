package com.biblioteca.app.data.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LibroTest : StringSpec({

    "el titulo debe coincidir con title" {
        val libro = Libro(userId = 1, id = 1, title = "mi titulo", body = "contenido")
        libro.titulo shouldBe "mi titulo"
    }

    "el contenido debe coincidir con body" {
        val libro = Libro(userId = 1, id = 1, title = "titulo", body = "mi contenido")
        libro.contenido shouldBe "mi contenido"
    }

    "el autorId debe coincidir con userId" {
        val libro = Libro(userId = 5, id = 1, title = "titulo", body = "contenido")
        libro.autorId shouldBe 5
    }
})
