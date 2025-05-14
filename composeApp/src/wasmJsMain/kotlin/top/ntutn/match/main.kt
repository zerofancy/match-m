package top.ntutn.match

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeViewport
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.browser.document
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getFontResourceBytes
import org.jetbrains.compose.resources.rememberResourceEnvironment
import top.ntutn.match.ui.AboutScreen
import top.ntutn.match.ui.GamePlayingScene
import top.ntutn.match.ui.GameScreen
import top.ntutn.match.ui.MenuScreen
import zmatch.composeapp.generated.resources.Res
import zmatch.composeapp.generated.resources.resource_han_rounded_cn_medium

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        MaterialTheme {
            val fontFamilyResolver = LocalFontFamilyResolver.current
            var fontsLoaded by remember { mutableStateOf(false) }
            val environment = rememberResourceEnvironment()

            LaunchedEffect(Unit) {
                val cjkFontBytes = getFontResourceBytes(environment, Res.font.resource_han_rounded_cn_medium)
                val fontFamily = FontFamily(listOf(Font("ResourceHanRounded", cjkFontBytes)))
                fontFamilyResolver.preload(fontFamily)
                fontsLoaded = true
            }

            if (fontsLoaded) {
                App()
            } else {
                Text("Loading Fonts...")
            }
        }
    }
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(GameScreen.MENU) }
    when (currentScreen) {
        GameScreen.MENU -> MenuScreen(
            onStart = {
                currentScreen = GameScreen.GAME_PLAYING
            },
            onAbout = {
                currentScreen = GameScreen.ABOUT
            },
            onExit = {}
        )

        GameScreen.GAME_PLAYING -> {
            val gameViewModel = viewModel { GameViewModel() }
            GamePlayingScene(gameViewModel = gameViewModel, exitApplication = {})
        }
        GameScreen.ABOUT -> AboutScreen {
            currentScreen = GameScreen.MENU
        }
    }
}