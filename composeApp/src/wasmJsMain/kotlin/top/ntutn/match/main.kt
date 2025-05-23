package top.ntutn.match

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeViewport
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getFontResourceBytes
import org.jetbrains.compose.resources.preloadImageBitmap
import org.jetbrains.compose.resources.rememberResourceEnvironment
import top.ntutn.match.ui.AboutScreen
import top.ntutn.match.ui.GamePlayingScene
import top.ntutn.match.ui.GameScreen
import top.ntutn.match.ui.MenuScreen
import top.ntutn.match.ui.SettingScreen
import top.ntutn.match.ui.theme.ZMatchTheme
import zmatch.composeapp.generated.resources.Res
import zmatch.composeapp.generated.resources.allDrawableResources
import zmatch.composeapp.generated.resources.resource_han_rounded_cn_medium

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        ZMatchTheme {
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.aspectRatio(0.618f)) {
                        App()
                    }
                }
            } else {
                Text("Loading Fonts...")
            }

            Res.allDrawableResources.values.forEach {
                // preload resources
                preloadImageBitmap(it)
            }
        }
    }
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(GameScreen.MENU) }
    val gameViewModel = viewModel { GameViewModel() }
    when (currentScreen) {
        GameScreen.MENU -> MenuScreen(
            onStart = {
                currentScreen = GameScreen.GAME_PLAYING
            },
            onSetting = {
                currentScreen = GameScreen.SETTING
            },
            onAbout = {
                currentScreen = GameScreen.ABOUT
            },
            onExit = {
                window.close()
            }
        )

        GameScreen.GAME_PLAYING -> {
            GamePlayingScene(gameViewModel = gameViewModel, exitApplication = {
                currentScreen = GameScreen.MENU
            })
        }
        GameScreen.ABOUT -> AboutScreen {
            currentScreen = GameScreen.MENU
        }

        GameScreen.SETTING -> {
            val difficulty by gameViewModel.difficulty.collectAsState()
            SettingScreen(difficulty, onDifficultyChange = gameViewModel::updateDifficulty) {
                currentScreen = GameScreen.MENU
            }
        }
    }
}