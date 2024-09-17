package eu.petrfaruzel.scratcher.core.compose.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.petrfaruzel.scratcher.core.compose.components.CircularProgressBar

@Composable
fun LoadingScreen(size: Dp = 40.dp, color: Color = Color.Black, fillMaxSize: Boolean) {
    CircularProgressBar(
        color = color,
        size = size,
        fillMaxSize = fillMaxSize,
    )
}

