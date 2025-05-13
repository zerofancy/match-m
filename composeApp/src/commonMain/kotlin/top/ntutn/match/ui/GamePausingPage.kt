package top.ntutn.match.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GamePausingPage(modifier: Modifier = Modifier, onResume: () -> Unit = {}, onExit: () -> Unit = {}) {
    Column(modifier) {
        Text("游戏已经暂停")
        Button(onClick = onResume) {
            Text("继续")
        }
        Button(onClick = onExit) {
            Text("退出")
        }
    }
}