package top.ntutn.common
import androidx.compose.foundation.Image
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun App(resourceItemCount:Int, getPainterById:@Composable (Int)->Painter) {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, ${getPlatformName()}"
        }) {
            Image(painter = getPainterById((0 until resourceItemCount).random()),contentDescription = null)
        }
    }
}
