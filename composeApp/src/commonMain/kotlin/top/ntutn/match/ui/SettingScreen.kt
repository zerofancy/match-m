package top.ntutn.match.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import zmatch.composeapp.generated.resources.Res
import zmatch.composeapp.generated.resources.setting_screen_exit
import zmatch.composeapp.generated.resources.setting_screen_options
import zmatch.composeapp.generated.resources.setting_screen_title

private val optionArray = arrayOf( // todo web字符串列表资源处理有问题，需要报告一下
    "简单",
    "普通",
    "困难"
)

@Composable
fun SettingScreen(
    difficulty: Difficulty,
    modifier: Modifier = Modifier,
    onDifficultyChange: (Difficulty) -> Unit = {},
    onBack: () -> Unit
) {
    Column(modifier = modifier) {
        Text(stringResource(Res.string.setting_screen_title), style = MaterialTheme.typography.headlineLarge)
        Difficulty.entries.forEachIndexed { index, item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = difficulty == item, onClick = { onDifficultyChange(item) })
//                Text(stringArrayResource(Res.array.setting_screen_options)[index])
                Text(optionArray[index])
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBack) {
            Text(stringResource(Res.string.setting_screen_exit))
        }
    }
}