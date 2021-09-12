package top.ntutn.android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import top.ntutn.common.AndroidGameViewModel
import top.ntutn.common.App
import top.ntutn.common.IViewModel
import top.ntutn.common.MahjongType
import top.ntutn.common.ui.theme.MTT
import top.ntutn.common.ui.theme.ZMatchTheme
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
        gameViewModel.init(N, N, mahjongSize, maxGameTime, stepGameTime)
        gameViewModel.start()
    }

    private val selectColorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
        setToSaturation(25f)
    })

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
                }

                Surface(color = MaterialTheme.colors.background) {
                    GamePage(
                        time = gameTimeState
                    )
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
                    weakActivity.get()?.handler?.sendEmptyMessageDelayed(MSG_TIME_TICK, 1000L)
                }
            }
        }
    }

    override fun onBackPressed() {
        gameViewModel.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    @Composable
    fun GamePage(time: Int) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Spacer(modifier = Modifier.height(48.dp))
                TimerArea(
                    time = time
                )
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                Board()
            }
        }
    }

    @Composable
    fun TimerArea(time: Int = 0) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = time.toString(),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            color = if (time > 5) MaterialTheme.colors.primary else MaterialTheme.colors.error
        )
    }

    @Composable
    fun Board() {
        Column {
            // 不显示最外圈空白
            val rows by gameViewModel.rows.collectAsState()
            val cols by gameViewModel.cols.collectAsState()
            for (i in 0 until rows) {
                if (i > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Row {
                    for (j in 0 until cols) {
                        val itemState by gameViewModel.mahjongArea[i + 1][j + 1].collectAsState()
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickable { gameViewModel.itemClick(i + 1, j + 1) }
                        ) {
                            Image(
                                painter = painterResource(id = itemState.id), // FIXME 这里显示逻辑有问题
                                contentDescription = null,
                                colorFilter = if (itemState.isSelected) selectColorFilter else null,
                                alpha = if (itemState.isDeleted) 0f else 1f
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * 当为LiveData赋值时，更改State的值为传入值
     * 适配现有区域刷新逻辑
     */
    @Composable
    private fun <R, T> LiveData<T>.observeWithData(data: R): State<R> {
        val lifecycleOwner = LocalLifecycleOwner.current
        val state = remember { mutableStateOf(data) }
        DisposableEffect(this, lifecycleOwner) {
            val observer = Observer<T> { state.value = data } // 忽略赋值，直接使用data
            observe(lifecycleOwner, observer)
            onDispose { removeObserver(observer) }
        }
        return state
    }
}