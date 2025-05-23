package top.ntutn.match

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import top.ntutn.match.ui.AboutScreen
import top.ntutn.match.ui.GamePlayingScene
import top.ntutn.match.ui.GameScreen
import top.ntutn.match.ui.MenuScreen
import top.ntutn.match.ui.SettingScreen
import top.ntutn.match.ui.theme.ZMatchTheme

class MainActivity : AppCompatActivity() {
    private val gameViewModel by viewModels<AndroidGameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZMatchTheme {
                var currentScreen by remember { mutableStateOf(GameScreen.MENU) }
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
                        onExit = ::finish
                    )

                    GameScreen.GAME_PLAYING -> {
                        GamePlayingScene(
                            gameViewModel = gameViewModel,
                            exitApplication = ::finish
                        )
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
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (gameViewModel.gameState.value == IViewModel.GameState.RUNNING) {
                    gameViewModel.pause()
                }
                // todo 弹出导航栈
            }
        })
    }
}