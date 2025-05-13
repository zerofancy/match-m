package top.ntutn.match

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.viewmodel.compose.viewModel
import top.ntutn.match.ui.AboutScreen
import top.ntutn.match.ui.GamePlayingScene
import top.ntutn.match.ui.GameScreen
import top.ntutn.match.ui.MenuScreen
import java.awt.EventQueue
import javax.swing.JOptionPane

fun main() = application {
    val state = rememberWindowState(size = DpSize(600.dp, 1000.dp))
    var exitConfirming by remember { mutableStateOf(false) }
    val exitQuery = {
        exitConfirming = true
        requestConfirm(
            "是否退出游戏？",
            "连连看",
            onOk = ::exitApplication,
            onCancel = {
                exitConfirming = false
            },
        )
    }
    Window(
        onCloseRequest = exitQuery,
        title = "连连看小游戏",
        state = state,
    ) {
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
                    onExit = exitQuery
                )

                GameScreen.GAME_PLAYING -> {
                    val gameViewModel = viewModel { GameViewModel() }
                    LaunchedEffect(exitConfirming) {
                        if (exitConfirming) {
                            gameViewModel.pause()
                        } else {
                            gameViewModel.resume()
                        }
                    }
                    GamePlayingScene(gameViewModel = gameViewModel, exitApplication = ::exitApplication)
                }
                GameScreen.ABOUT -> AboutScreen {
                    currentScreen = GameScreen.MENU
                }
            }
        }
    }
}

private fun requestConfirm(
    message: String,
    title: String,
    onOk: () -> Unit,
    onCancel: () -> Unit = {},
) {
    EventQueue.invokeLater {
        val option = JOptionPane.showConfirmDialog(
            null,
            message, title,
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE
        )
        if (option == JOptionPane.OK_OPTION) {
            onOk()
        } else {
            onCancel()
        }
    }
}

