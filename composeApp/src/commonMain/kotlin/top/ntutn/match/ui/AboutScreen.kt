package top.ntutn.match.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AboutScreen(modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    Column(modifier) {
        Text("关于")
        Button(onClick = onBack) {
            Text("返回")
        }
    }
}