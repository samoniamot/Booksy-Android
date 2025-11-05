package com.biblioteca.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

// animacion simple para mostrar cosas
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimacionSimple(
    visible: Boolean,
    contenido: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut()
    ) {
        contenido()
    }
}

