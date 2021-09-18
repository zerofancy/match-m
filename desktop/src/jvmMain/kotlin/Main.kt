import androidx.compose.desktop.AppWindow
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import top.ntutn.common.App
import top.ntutn.common.GameViewModel
import top.ntutn.common.IViewModel
import top.ntutn.common.ui.GamePage
import java.awt.EventQueue
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JOptionPane
import javax.swing.SwingUtilities
import javax.swing.Timer
import kotlin.concurrent.thread

// 麻将牌是一个N*N的区域，所以N必须是偶数
private const val N = 10

// 最多使用的麻将的种类，越多游戏就越难
private const val mahjongSize = 15

// 最大倒计时时间，单位秒
private const val maxGameTime = 15

// 每完成一次增加的游戏时间
private const val stepGameTime = 3

fun main() = SwingUtilities.invokeLater {
    AppWindow().apply {
        setContentSize(600, 1000)
        val gameViewModel: IViewModel = GameViewModel()

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

        show {
            MaterialTheme {

                val gameState by gameViewModel.gameState.collectAsState()
                val gameTimeState by gameViewModel.gameTime.collectAsState()

                Surface(color = MaterialTheme.colors.background) {
                    GamePage(
                        time = gameTimeState,
                        gameViewModel
                    ) {
                        BitmapPainter(imageFromResource("drawable/${MahjongDesktop.all[it]}.png"))
                    }
                }
            }
        }

        startGame()

        GlobalScope.launch {
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
                                System.exit(0)
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
                            close()
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
                            close()
                        }
                    }
                }
            }
        }

        Timer(1000, object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                gameViewModel.timeTick()
            }
        }).start()
    }

}

private fun AppWindow.setContentSize(width: Int, height: Int) {
    window.pack()
    val insets = window.insets
    setSize(width + insets.left + insets.right, height + insets.top + insets.bottom)
}