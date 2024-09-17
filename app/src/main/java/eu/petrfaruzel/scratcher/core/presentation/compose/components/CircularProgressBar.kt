package eu.petrfaruzel.scratcher.core.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun CircularProgressBar(
    color: Color,
    size: Dp,
    fillMaxSize: Boolean,
) {
    Box(
        modifier = if (fillMaxSize) Modifier.fillMaxSize() else Modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(size), color = color)
    }
}