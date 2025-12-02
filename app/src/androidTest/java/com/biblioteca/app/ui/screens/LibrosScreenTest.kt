package com.biblioteca.app.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.biblioteca.app.data.model.Libro
import com.biblioteca.app.ui.viewmodel.LibrosViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class LibrosScreenTest {

    @get:Rule
    val reglaCompose = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun el_titulo_del_libro_debe_aparecer_en_pantalla() {
        val librosFalsos = listOf(
            Libro(id = "1", titulo = "libro de prueba uno", descripcion = "contenido uno", imagen = "https://ejemplo.com/1.jpg", precio = 15990.0),
            Libro(id = "2", titulo = "libro de prueba dos", descripcion = "contenido dos", imagen = "https://ejemplo.com/2.jpg", precio = 12500.0)
        )

        val viewModelFalso = object : LibrosViewModel() {
            override val librosFiltrados = MutableStateFlow(librosFalsos)
        }

        reglaCompose.setContent {
            LibrosScreenConViewModel(viewModel = viewModelFalso)
        }

        reglaCompose.onNodeWithText("libro de prueba uno").assertIsDisplayed()
        reglaCompose.onNodeWithText("libro de prueba dos").assertIsDisplayed()
    }
}
