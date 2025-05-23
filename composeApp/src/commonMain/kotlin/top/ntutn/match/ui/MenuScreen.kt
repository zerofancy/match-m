package top.ntutn.match.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zmatch.composeapp.generated.resources.Res
import zmatch.composeapp.generated.resources.ic_match
import zmatch.composeapp.generated.resources.menu_screen_about
import zmatch.composeapp.generated.resources.menu_screen_exit
import zmatch.composeapp.generated.resources.menu_screen_setting
import zmatch.composeapp.generated.resources.menu_screen_start
import zmatch.composeapp.generated.resources.menu_screen_title

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onStart: () -> Unit = {},
    onSetting: () -> Unit = {},
    onAbout: () -> Unit = {},
    onExit: () -> Unit = {},
) {
    Column(modifier = modifier) {
        val modifier = Modifier.align(Alignment.CenterHorizontally)
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(Res.drawable.ic_match), null)
            Text(stringResource(Res.string.menu_screen_title), style = MaterialTheme.typography.headlineLarge)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onStart, modifier = modifier) {
            Text(stringResource(Res.string.menu_screen_start))
        }
        Button(onClick = onSetting, modifier = modifier) {
            Text(stringResource(Res.string.menu_screen_setting))
        }
        Button(onClick = onAbout, modifier = modifier) {
            Text(stringResource(Res.string.menu_screen_about))
        }
        Button(onClick = onExit, modifier = modifier) {
            Text(stringResource(Res.string.menu_screen_exit))
        }
    }
}