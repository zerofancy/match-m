package top.ntutn.match.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import zmatch.composeapp.generated.resources.Res
import zmatch.composeapp.generated.resources.ic_match

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier.Companion.fillMaxSize(),
    onStart: () -> Unit = {},
    onAbout: () -> Unit = {},
    onExit: () -> Unit = {}
) {
    Column(modifier = modifier) {
        val modifier = Modifier.Companion.align(Alignment.Companion.CenterHorizontally)
        Row(modifier = modifier) {
            Image(painterResource(Res.drawable.ic_match), null)
            Text("连连看游戏")
        }
        Button(onClick = onStart, modifier = modifier) {
            Text("开始")
        }
        Button(onClick = onAbout, modifier = modifier) {
            Text("关于")
        }
        Button(onClick = onExit, modifier = modifier) {
            Text("退出")
        }
    }
}