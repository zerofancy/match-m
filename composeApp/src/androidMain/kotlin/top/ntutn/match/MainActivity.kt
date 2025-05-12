package top.ntutn.match

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import top.ntutn.match.ui.GamePage
import top.ntutn.match.ui.theme.MTT
import top.ntutn.match.ui.theme.ZMatchTheme
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    companion object {
        // 麻将牌是一个N*N的区域，所以N必须是偶数
        private const val N = 10

        // 最多使用的麻将的种类，越多游戏就越难
        private const val mahjongSize = 15

        // 最大倒计时时间，单位秒
        private const val maxGameTime = 15

        // 每完成一次增加的游戏时间
        private const val stepGameTime = 3

        private const val MSG_TIME_TICK = 1
    }

    private val gameViewModel by viewModels<AndroidGameViewModel>()
    private val handler = MyHandler(this, Looper.getMainLooper())

    private val startGame = {
        gameViewModel.init(N, N, mahjongSize, maxGameTime, stepGameTime, MahjongAndroid.front.size)
        gameViewModel.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZMatchTheme {
                val gameState by gameViewModel.gameState.collectAsState()
                val gameTimeState by gameViewModel.gameTime.collectAsState()

                when (gameState) {
                    IViewModel.GameState.PAUSE -> MTT.SimpleDialog(
                        title = "游戏暂停",
                        content = "是否退出游戏？",
                        confirmButton = "继续游戏" to { gameViewModel.resume() },
                        cancelButton = "退出" to { finish() },
                        onCancel = { /*点击空白区域啥也不做*/ }
                    )
                    IViewModel.GameState.SUCCEEDED -> MTT.SimpleDialog(
                        title = "游戏胜利",
                        content = "恭喜你取得胜利！",
                        confirmButton = "再来一把" to startGame,
                        cancelButton = "退出" to { finish() },
                        onCancel = {/*点击空白区域啥也不做*/ }
                    )
                    IViewModel.GameState.FAILED -> MTT.SimpleDialog(
                        title = "游戏失败",
                        content = "很遗憾你输了。",
                        confirmButton = "再来一把" to startGame,
                        cancelButton = "退出" to { finish() },
                        onCancel = {/*点击空白区域啥也不做*/ }
                    )

                    IViewModel.GameState.PENDING -> {}
                    IViewModel.GameState.RUNNING -> {}
                }

                Surface(color = MaterialTheme.colors.background) {
                    GamePage(
                        time = gameTimeState,
                        gameViewModel
                    ) {
                        painterResource(MahjongAndroid.front[it])
                    }
                }
            }
        }

        startGame.invoke()
        handler.sendEmptyMessageDelayed(MSG_TIME_TICK, 1000L)
    }

    private class MyHandler(activity: MainActivity, looper: Looper) : Handler(looper) {
        private val weakActivity = WeakReference<MainActivity>(activity)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_TIME_TICK -> {
                    weakActivity.get()?.gameViewModel?.timeTick()
                    weakActivity.get()?.handler?.removeMessages(MSG_TIME_TICK)
                    weakActivity.get()?.handler?.sendEmptyMessageDelayed(MSG_TIME_TICK, 1000L)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        gameViewModel.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}