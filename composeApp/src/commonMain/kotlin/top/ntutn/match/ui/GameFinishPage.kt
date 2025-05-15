package top.ntutn.match.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GameFinishPage(win: Boolean, modifier: Modifier = Modifier, onRestart: () -> Unit = {}, onExit: () -> Unit = {}) {
    Column(modifier) {
        Text(if (win) "你赢了" else "你输了")
        Button(onClick = onRestart) {
            Text("再来一局")
        }
        Button(onClick = onExit) {
            Text("返回")
        }
    }
}