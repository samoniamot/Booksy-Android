package com.biblioteca.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// animacion para mostrar errores
@Composable
fun ErrorAnimado(
    mensage: String?,
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { -40 },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mensage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// animacion para el boton de carga
@Composable
fun BotonConCarga(
    texto: String,
    cargando: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val colorAnimado by animateColorAsState(
        targetValue = if (cargando) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.primary
        },
        animationSpec = tween(durationMillis = 300)
    )
    
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !cargando,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = colorAnimado
        )
    ) {
        AnimatedContent(
            targetState = cargando,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            }
        ) { estaCargando ->
            if (estaCargando) {
                androidx.compose.material3.CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(texto)
            }
        }
    }
}

// transicion suave entre pantallas
@Composable
fun TransicionPantalla(
    contenido: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(500)) +
                slideInHorizontally(
                    initialOffsetX = { 100 },
                    animationSpec = tween(500)
                )
    ) {
        contenido()
    }
}
