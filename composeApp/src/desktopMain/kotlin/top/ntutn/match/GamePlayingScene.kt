package top.ntutn.match

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import top.ntutn.match.ui.GamePage
import java.awt.EventQueue
import javax.swing.JOptionPane

// 麻将牌是一个N*N的区域，所以N必须是偶数
private const val N = 10

// 最多使用的麻将的种类，越多游戏就越难
private const val mahjongSize = 15

// 最大倒计时时间，单位秒
private const val maxGameTime = 15

// 每完成一次增加的游戏时间
private const val stepGameTime = 3

@Composable
fun GamePlayingScene(modifier: Modifier = Modifier.Companion, exitApplication: () -> Unit) {
    val gameViewModel = viewModel { GameViewModel() }

    val gameState by gameViewModel.gameState.collectAsState()
    val gameTimeState by gameViewModel.gameTime.collectAsState()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = modifier,
    ) {
        when (gameState) {
            else -> GamePage(
                time = gameTimeState,
                gameViewModel
            ) {
                painterResource("drawable/${MahjongDesktop.all[it]}.png")
            }
        }
    }

    val startGame = {
        gameViewModel.init(
            N,
            N,
            mahjongSize,
            maxGameTime,
            stepGameTime,
            MahjongDesktop.front.size
        )
        gameViewModel.start()
    }

    LaunchedEffect(Unit) {
        gameViewModel.gameState.collect {
            println("游戏状态：$it")
            when (it) {
                IViewModel.GameState.SUCCEEDED -> {
                    EventQueue.invokeLater {
                        val option = JOptionPane.showConfirmDialog(
                            null,
                            "你赢了。是否再来一局？",
                            "你赢了",
                            JOptionPane.OK_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                        )
                        if (option == JOptionPane.OK_OPTION) {
                            startGame()
                        } else {
                            exitApplication()
                        }
                    }
                }

                IViewModel.GameState.FAILED -> {
                    val option = JOptionPane.showConfirmDialog(
                        null,
                        "很遗憾你输了。是否再来一局？",
                        "你输了",
                        JOptionPane.OK_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    )
                    if (option == JOptionPane.OK_OPTION) {
                        startGame()
                    } else {
                        exitApplication()
                    }
                }

                IViewModel.GameState.PAUSE -> {
                    val option = JOptionPane.showConfirmDialog(
                        null,
                        "是否退出游戏？",
                        "游戏暂停",
                        JOptionPane.OK_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    )
                    if (option == JOptionPane.OK_OPTION) {
                        gameViewModel.resume()
                    } else {
                        exitApplication()
                    }
                }

                IViewModel.GameState.PENDING -> {}
                IViewModel.GameState.RUNNING -> {}
            }
        }
    }
    LaunchedEffect(Unit) {
        startGame()
        while (true) {
            delay(1000)
            gameViewModel.timeTick()
        }
    }
}