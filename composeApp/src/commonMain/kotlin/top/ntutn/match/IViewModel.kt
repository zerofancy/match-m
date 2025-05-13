package top.ntutn.match

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IViewModel {
    val mahjongArea: StateFlow<Array<Array<MutableStateFlow<MahjongType>>>>
    val gameState: StateFlow<GameState>
    val gameTime: StateFlow<Int>
    val rows: StateFlow<Int>
    val cols: StateFlow<Int>
    fun init(
        rows: Int,
        cols: Int,
        itemCount: Int,
        maxGameTime: Int,
        stepGameTime: Int,
        selectableItemCount: Int = 0
    )

    fun start()
    fun pause()
    fun resume()
    fun timeTick()
    fun itemClick(row: Int, col: Int)

    enum class GameState {
        PENDING,
        RUNNING,
        PAUSE,
        SUCCEEDED,
        FAILED
    }
}