package top.ntutn.match.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AboutScreen(modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    Column(modifier) {
        Text("连连看游戏介绍")
        Text("""
            连连看是一个经典益智小游戏，它简单好上手，又能锻炼思维反应速度。
            依次点击两张麻将牌，如果它们拥有一样的图标，且能在两次拐弯以内相连，那么就可以被消除。
            消除麻将牌后将获得时间奖励，在游戏时间耗尽前消除所有麻将牌。
        """.trimIndent())
        Button(onClick = onBack) {
            Text("返回")
        }
    }
}