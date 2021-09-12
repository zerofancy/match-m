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
import top.ntutn.common.App
import top.ntutn.common.GameViewModel
import top.ntutn.common.IViewModel
import top.ntutn.common.ui.GamePage
import javax.swing.SwingUtilities

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

        show {
            MaterialTheme {
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

                val gameState by gameViewModel.gameState.collectAsState()
                val gameTimeState by gameViewModel.gameTime.collectAsState()

                // TODO 游戏状态和弹窗 & 时间
//        when (gameState) {
//            IViewModel.GameState.PAUSE -> MTT.SimpleDialog(
//                title = "游戏暂停",
//                content = "是否退出游戏？",
//                confirmButton = "继续游戏" to { gameViewModel.resume() },
//                cancelButton = "退出" to { finish() },
//                onCancel = { /*点击空白区域啥也不做*/ }
//            )
//            IViewModel.GameState.SUCCEEDED -> MTT.SimpleDialog(
//                title = "游戏胜利",
//                content = "恭喜你取得胜利！",
//                confirmButton = "再来一把" to startGame,
//                cancelButton = "退出" to { finish() },
//                onCancel = {/*点击空白区域啥也不做*/ }
//            )
//            IViewModel.GameState.FAILED -> MTT.SimpleDialog(
//                title = "游戏失败",
//                content = "很遗憾你输了。",
//                confirmButton = "再来一把" to startGame,
//                cancelButton = "退出" to { finish() },
//                onCancel = {/*点击空白区域啥也不做*/ }
//            )
//        }

                Surface(color = MaterialTheme.colors.background) {
                    GamePage(
                        time = gameTimeState,
                        gameViewModel
                    ) {
                        BitmapPainter(imageFromResource("drawable/${MahjongDesktop.all[it]}.png"))
                    }
                }

                startGame()

            }
        }
    }
}

private fun AppWindow.setContentSize(width: Int, height: Int) {
    window.pack()
    val insets = window.insets
    setSize(width + insets.left + insets.right, height + insets.top + insets.bottom)
}