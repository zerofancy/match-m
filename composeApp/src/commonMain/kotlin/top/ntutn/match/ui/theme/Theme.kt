package top.ntutn.match.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import kotlin.random.Random

@Composable
fun ZMatchTheme(isDark: Boolean = false, content: @Composable() () -> Unit) {
    val red = remember { Random.nextInt(256) }
    val green = remember { Random.nextInt(256) }
    val blue = remember { Random.nextInt(256) }

    DynamicMaterialTheme(
        seedColor = Color(red, green, blue),
        isDark = isDark,
        isAmoled = true,
        shapes = MaterialTheme.shapes,
        content = content,
    )
}