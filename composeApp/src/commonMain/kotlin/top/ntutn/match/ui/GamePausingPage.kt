package top.ntutn.match.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import zmatch.composeapp.generated.resources.Res
import zmatch.composeapp.generated.resources.game_pause_continue_button
import zmatch.composeapp.generated.resources.game_pause_exit_button
import zmatch.composeapp.generated.resources.game_pause_tip

@Composable
fun GamePausingPage(modifier: Modifier = Modifier, onResume: () -> Unit = {}, onExit: () -> Unit = {}) {
    Column(modifier) {
        Text(stringResource(Res.string.game_pause_tip))
        Button(onClick = onResume) {
            Text(stringResource(Res.string.game_pause_continue_button))
        }
        Button(onClick = onExit) {
            Text(stringResource(Res.string.game_pause_exit_button))
        }
    }
}
