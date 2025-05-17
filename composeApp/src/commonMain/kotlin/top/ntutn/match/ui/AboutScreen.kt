package top.ntutn.match.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import zmatch.composeapp.generated.resources.Res
import zmatch.composeapp.generated.resources.about_screen_about
import zmatch.composeapp.generated.resources.about_screen_title
import zmatch.composeapp.generated.resources.about_screen_back

@Composable
fun AboutScreen(modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    Column(modifier) {
        Text(stringResource(Res.string.about_screen_title))
        Text(stringResource(Res.string.about_screen_about).trimIndent())
        Button(onClick = onBack) {
            Text(stringResource(Res.string.about_screen_back))
        }
    }
}