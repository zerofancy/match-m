package top.ntutn.match

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import top.ntutn.match.ui.Difficulty

interface IViewModel {
    val difficulty: StateFlow<Difficulty>
    val mahjongArea: StateFlow<Array<Array<MutableStateFlow<MahjongType>>>>
    val gameState: StateFlow<GameState>
    val gameTime: StateFlow<Int>
    val rows: StateFlow<Int>
    val cols: StateFlow<Int>
    fun init(
        rows: Int,
        cols: Int,
        itemCount: Int,
        selectableItemCount: Int = 0
    )

    fun start()
    fun pause()
    fun resume()
    fun timeTick()
    fun itemClick(row: Int, col: Int)
    fun updateDifficulty(difficulty: Difficulty)

    enum class GameState {
        PENDING,
        RUNNING,
        PAUSE,
        SUCCEEDED,
        FAILED
    }
}