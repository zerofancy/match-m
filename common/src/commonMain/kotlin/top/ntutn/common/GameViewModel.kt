package top.ntutn.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel : IViewModel {
    override var mahjongArea: Array<Array<MutableStateFlow<MahjongType>>> = arrayOf(
        arrayOf(
            MutableStateFlow(
                MahjongType(id = 0, false, true)
            ),
            MutableStateFlow(
                MahjongType(id = 0, false, true)
            )
        ),
        arrayOf(
            MutableStateFlow(
                MahjongType(id = 0, false, true)
            ),
            MutableStateFlow(
                MahjongType(id = 0, false, true)
            )
        )
    )
    private var selectedIndex: Pair<Int, Int>? = null

    // 游戏状态
    private val _gameState = MutableStateFlow(IViewModel.GameState.PENDING)
    override val gameState: StateFlow<IViewModel.GameState>
        get() = _gameState

    private var maxGameTime = 10
    private var stepGameTime = 3
    private var selectableItemCount = 0
    private val _gameTime = MutableStateFlow(10)
    override val gameTime: StateFlow<Int>
        get() = _gameTime

    private val _rows = MutableStateFlow(0)
    override val rows: StateFlow<Int>
        get() = _rows
    private val _cols = MutableStateFlow(0)
    override val cols: StateFlow<Int>
        get() = _cols

    /**
     * 初始化游戏
     * @param rows 游戏区域行数
     * @param cols 游戏区域列数
     * @param itemCount 使用的麻将牌的数量
     */
    override fun init(
        rows: Int,
        cols: Int,
        itemCount: Int,
        maxGameTime: Int,
        stepGameTime: Int,
        selectableItemCount: Int
    ) {
        require(rows * cols % 2 == 0) { "区域应该有偶数个元素" }
        require(itemCount > 0) { "备选资源不足" }
        require(itemCount <= selectableItemCount) { "麻将牌资源不足" }
        require(maxGameTime > 5) { "游戏时间过短" }
        require(stepGameTime >= 0) { "stepGameTime参数错误" }

        // +2是为了给周围放上一圈空格子，计算的时候方便
        mahjongArea = Array(rows + 2) {
            Array(cols + 2) {
                MutableStateFlow(
                    MahjongType(
                        id = 0,
                        isSelected = false,
                        isDeleted = true
                    )
                )
            }
        }

        this.selectableItemCount = selectableItemCount
        this.maxGameTime = maxGameTime
        this.stepGameTime = stepGameTime

        _rows.value = rows
        _cols.value = cols

        val totalItemCollection = 0 until selectableItemCount
        val res = mutableListOf<MahjongType>()
        while (res.size < rows * cols) {
            val item = MahjongType(id = totalItemCollection.random())
            res.add(item)
            // 深拷贝
            res.add(item.copy())
        }
        res.shuffle()
        for (i in res.indices) {
            mahjongArea[i / cols + 1][i % cols + 1].value = res[i]
        }
        selectedIndex = null
        _gameState.value = IViewModel.GameState.PENDING
    }


    /**
     * 开始游戏
     */
    override fun start() {
        if (_gameState.value != IViewModel.GameState.PENDING) {
            return
        }
        _gameState.value = IViewModel.GameState.RUNNING
        _gameTime.value = maxGameTime
    }

    /**
     * 游戏暂停
     */
    override fun pause() {
        if (_gameState.value != IViewModel.GameState.RUNNING) {
            return
        }
        _gameState.value = IViewModel.GameState.PAUSE
    }

    /**
     * 继续游戏
     */
    override fun resume() {
        if (_gameState.value != IViewModel.GameState.PAUSE) {
            return
        }
        _gameState.value = IViewModel.GameState.RUNNING
    }

    /**
     * 每秒钟被调用，计算游戏倒计时
     */
    override fun timeTick() {
        if (_gameState.value != IViewModel.GameState.RUNNING) {
            return
        }
        val gameTime = (_gameTime.value ?: 1) - 1
        _gameTime.value = gameTime
        if (gameTime <= 0) {
            _gameState.value = IViewModel.GameState.FAILED
        }
    }


    /**
     * 点击了某坐标
     * 注意：游戏区域最外圈还有一些格子
     * @param row 当前行数（1开始）
     * @param col 当前列（1开始）
     */
    override fun itemClick(row: Int, col: Int) {
        if (mahjongArea[row][col].value.isDeleted) {
            // 当前点击元素已经消除
            return
        }
        if (selectedIndex == null) {
            // 没有已经选中的，选中点击项
            mahjongArea[row][col].value = mahjongArea[row][col].value.copy(isSelected = true)
            selectedIndex = row to col
            return
        }
        val previousSelected = mahjongArea[selectedIndex!!.first][selectedIndex!!.second]
        val currentSelected = mahjongArea[row][col]
        // 判断是否可消除
        if (checkIsCanDelete(row to col, selectedIndex!!)) {
            previousSelected.value =
                previousSelected.value.copy(isSelected = false, isDeleted = true)
            currentSelected.value = currentSelected.value.copy(isDeleted = true)
            selectedIndex = null
            val gameTime = (_gameTime.value ?: 1) + stepGameTime
            _gameTime.value = gameTime.takeIf { it <= maxGameTime } ?: maxGameTime
            if (mahjongArea.all { it.all { it.value.isDeleted } }) {
                // 所有麻将已经消除，游戏胜利
                _gameState.value = IViewModel.GameState.SUCCEEDED
            }
        } else {
            previousSelected.value = previousSelected.value.copy(isSelected = false)
            selectedIndex = null
        }
    }

    /**
     * 判断两个元素是否能消除
     */
    private fun checkIsCanDelete(itemIndex1: Pair<Int, Int>, itemIndex2: Pair<Int, Int>): Boolean {
        if (itemIndex1 == itemIndex2) {
            return false
        }
        if (mahjongArea.getByPair(itemIndex1).value.id != mahjongArea.getByPair(itemIndex2).value.id) {
            return false
        }
        return VisitDirection.values().any {
            checkIsCanMatch(itemIndex1, itemIndex2, it)
        }
    }

    /**
     * 判断两个在不同位置的相同元素是否能消除
     * BFS
     */
    private fun checkIsCanMatch(
        currentPoint: Pair<Int, Int>,
        targetPoint: Pair<Int, Int>,
        visitDirection: VisitDirection,
        maxRounds: Int = 2
    ): Boolean {
        val nextPoints = currentPoint.getNextPoints(visitDirection, targetPoint).filter {
            if (maxRounds > 0) true else it.third == 0
        }
        if (targetPoint in nextPoints.map { it.first }) {
            return true
        }
        return nextPoints.isNotEmpty() && nextPoints.any {
            checkIsCanMatch(it.first, targetPoint, it.second, maxRounds - it.third)
        }
    }

    /**
     * 当前结点是否在游戏区域内
     */
    private fun Pair<Int, Int>.isPointValid() =
        this.first in 0..(rows.value + 1) && this.second in 0..(cols.value + 1)

    /**
     * 当前访问方向
     */
    private enum class VisitDirection {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    /**
     * 获取当前点的下一个点
     * @param currentDirection 当前朝向的方向
     * @return 一个集合，集合中有0～3个三元组，每个三元组有{点，朝向，需要拐弯次数}
     */
    private fun Pair<Int, Int>.getNextPoints(
        currentDirection: VisitDirection,
        targetPoint: Pair<Int, Int>
    ): Set<Triple<Pair<Int, Int>, VisitDirection, Int>> {
        val res = mutableSetOf<Triple<Pair<Int, Int>, VisitDirection, Int>>()
        val nextPoint = when (currentDirection) {
            VisitDirection.TOP -> first - 1 to second
            VisitDirection.BOTTOM -> first + 1 to second
            VisitDirection.LEFT -> first to second - 1
            VisitDirection.RIGHT -> first to second + 1
        }
        val (leftPoint, leftDirection) = when (currentDirection) {
            VisitDirection.TOP -> first to second - 1 to VisitDirection.LEFT
            VisitDirection.BOTTOM -> first to second + 1 to VisitDirection.RIGHT
            VisitDirection.LEFT -> first + 1 to second to VisitDirection.BOTTOM
            VisitDirection.RIGHT -> first - 1 to second to VisitDirection.TOP
        }
        val (rightPoint, rightDirection) = when (currentDirection) {
            VisitDirection.TOP -> first to second + 1 to VisitDirection.RIGHT
            VisitDirection.BOTTOM -> first to second - 1 to VisitDirection.LEFT
            VisitDirection.LEFT -> first - 1 to second to VisitDirection.TOP
            VisitDirection.RIGHT -> first + 1 to second to VisitDirection.BOTTOM
        }
        if (nextPoint.isPointCanUse(targetPoint)) {
            res.add(Triple(nextPoint, currentDirection, 0))
        }
        if (leftPoint.isPointCanUse(targetPoint)) {
            res.add(Triple(leftPoint, leftDirection, 1))
        }
        if (rightPoint.isPointCanUse(targetPoint)) {
            res.add(Triple(rightPoint, rightDirection, 1))
        }
        return res
    }

    /**
     * 判断某个点在游戏区域内，而且是空白格子或目标格子
     */
    private fun Pair<Int, Int>.isPointCanUse(targetPoint: Pair<Int, Int>) =
        isPointValid() && (mahjongArea.getByPair(this).value.isDeleted || this == targetPoint)

}

