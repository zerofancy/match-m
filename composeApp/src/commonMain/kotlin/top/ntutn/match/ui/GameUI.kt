package top.ntutn.match.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import top.ntutn.match.IViewModel

private val selectColorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
    setToSaturation(25f)
})

@Composable
fun GamePage(time: Int, gameViewModel: IViewModel, getPainterById: @Composable (Int) -> Painter) {
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
            Board(gameViewModel, getPainterById)
        }
    }
}

@Composable
fun TimerArea(time: Int = 0) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = time.toString(),
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        color = if (time > 5) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    )
}

@Composable
fun Board(gameViewModel: IViewModel, getPainterById: @Composable (Int) -> Painter) {
    // 不显示最外圈空白
    val rows by gameViewModel.rows.collectAsState()
    val cols by gameViewModel.cols.collectAsState()
    val connectionLine by gameViewModel.connectionLine.collectAsState()
    val primaryColor = MaterialTheme.colorScheme.primary
    
    Box {
        // 绘制连线
        if (connectionLine != null) {
            val path = connectionLine!!
            // 计算路径点的坐标（注意：路径点是 1-based 的坐标）
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        if (path.size >= 2) {
                            val points = path.map { point ->
                                Offset(
                                    (point.second - 0.5f) / cols * size.width,
                                    (point.first - 0.5f) / rows * size.height
                                )
                            }
                            
                            // 绘制连线
                            for (i in 0 until points.size - 1) {
                                drawLine(
                                    color = primaryColor,
                                    start = points[i],
                                    end = points[i + 1],
                                    strokeWidth = 4f
                                )
                            }
                        }
                    }
            )
        }
        
        // 绘制麻将牌
        Column {
            for (i in 0 until rows) {
                if (i > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Row {
                    for (j in 0 until cols) {
                        val area by gameViewModel.mahjongArea.collectAsState()
                        val itemState by area[i + 1][j + 1].collectAsState()
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickable { gameViewModel.itemClick(i + 1, j + 1) }
                        ) {
                            Image(
                                painter = getPainterById(itemState.id),
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
}