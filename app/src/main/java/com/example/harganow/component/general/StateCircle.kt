package com.example.harganow.component.general

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private fun Dp.toPx(density: Density): Float {
    return (this.value * density.density).toFloat()
}

@Composable
fun StateCircle(
    isChosen: Boolean,
    circleColor: Color = Color.Blue,
    circleSize: Dp = 40.dp,
    circleOutlineSize: Dp = 2.dp,
    outlineColor: Color = Color.Gray,
    onClick: () -> Unit
) {
    val density = LocalDensity.current
    val stroke = if (isChosen) null else Stroke(width = circleOutlineSize.toPx(density))
    val color = if (isChosen) circleColor else Color.Transparent
    Box(
        modifier = Modifier
            .size(circleSize)
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (stroke != null) {
                drawCircle(color = outlineColor, style = stroke)
            }
            drawCircle(color = color)
        }
    }
}
