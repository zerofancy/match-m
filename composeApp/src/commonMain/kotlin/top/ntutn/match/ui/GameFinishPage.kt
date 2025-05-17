package top.ntutn.match.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import zmatch.composeapp.generated.resources.Res
import zmatch.composeapp.generated.resources.game_finish_close
import zmatch.composeapp.generated.resources.game_finish_lose
import zmatch.composeapp.generated.resources.game_finish_retry
import zmatch.composeapp.generated.resources.game_finish_win

@Composable
fun GameFinishPage(win: Boolean, modifier: Modifier = Modifier, onRestart: () -> Unit = {}, onExit: () -> Unit = {}) {
    Column(modifier) {
        Text(stringResource(if (win) Res.string.game_finish_win else Res.string.game_finish_lose))
        Button(onClick = onRestart) {
            Text(stringResource(Res.string.game_finish_retry))
        }
        Button(onClick = onExit) {
            Text(stringResource(Res.string.game_finish_close))
        }
    }
}