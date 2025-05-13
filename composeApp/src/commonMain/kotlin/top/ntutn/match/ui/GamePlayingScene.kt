package top.ntutn.match.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import top.ntutn.match.GameViewModel
import top.ntutn.match.IViewModel
import top.ntutn.match.MahjongRes

// 麻将牌是一个N*N的区域，所以N必须是偶数
private const val N = 10

// 最多使用的麻将的种类，越多游戏就越难
private const val mahjongSize = 15

// 最大倒计时时间，单位秒
private const val maxGameTime = 15

// 每完成一次增加的游戏时间
private const val stepGameTime = 3

@Composable
fun GamePlayingScene(
    modifier: Modifier = Modifier,
    gameViewModel: IViewModel = viewModel { GameViewModel() },
    exitApplication: () -> Unit = {}
) {
    val gameState by gameViewModel.gameState.collectAsState()
    val gameTimeState by gameViewModel.gameTime.collectAsState()
    val startGame = {
        gameViewModel.init(
            N,
            N,
            mahjongSize,
            maxGameTime,
            stepGameTime,
            MahjongRes.front.size
        )
        gameViewModel.start()
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = modifier,
    ) {
        when (gameState) {
            IViewModel.GameState.SUCCEEDED -> GameFinishPage(
                win = true,
                onRestart = startGame,
                onExit = exitApplication,
            )
            IViewModel.GameState.FAILED -> GameFinishPage(
                win = false,
                onRestart = startGame,
                onExit = exitApplication,
            )
            IViewModel.GameState.PAUSE -> GamePausingPage(
                onResume = gameViewModel::resume,
                onExit = exitApplication,
            )
            else -> GamePage(
                time = gameTimeState,
                gameViewModel
            ) {
                painterResource(MahjongRes.all[it])
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