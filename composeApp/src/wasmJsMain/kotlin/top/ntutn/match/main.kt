package top.ntutn.match

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.browser.document
import top.ntutn.match.ui.AboutScreen
import top.ntutn.match.ui.GamePlayingScene
import top.ntutn.match.ui.GameScreen
import top.ntutn.match.ui.MenuScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        MaterialTheme {
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
    }
}