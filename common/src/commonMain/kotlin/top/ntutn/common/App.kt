package top.ntutn.common

import androidx.compose.foundation.Image
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun App(resourceItemCount: Int, getPainterById: @Composable (Int) -> Painter) {
    MaterialTheme {
        Image(
            painter = getPainterById((0 until resourceItemCount).random()),
            contentDescription = null
        )
    }
}
