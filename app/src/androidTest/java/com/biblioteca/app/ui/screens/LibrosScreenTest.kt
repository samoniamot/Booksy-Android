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
            Libro(userId = 1, id = 1, title = "libro de prueba uno", body = "contenido uno"),
            Libro(userId = 2, id = 2, title = "libro de prueba dos", body = "contenido dos")
        )

        val viewModelFalso = object : LibrosViewModel() {
            override val librosFiltrados = MutableStateFlow(librosFalsos)
        }

        reglaCompose.setContent {
            LibrosScreenConViewModel(viewModel = viewModelFalso)
        }

        reglaCompose.onNodeWithText("libro de prueba 1").assertIsDisplayed()
        reglaCompose.onNodeWithText("libro de prueba 2").assertIsDisplayed()
    }
}
