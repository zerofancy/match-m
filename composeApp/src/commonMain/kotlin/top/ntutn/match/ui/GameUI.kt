package top.ntutn.match.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
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
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Center,
        color = if (time > 5) MaterialTheme.colors.primary else MaterialTheme.colors.error
    )
}

@Composable
fun Board(gameViewModel: IViewModel, getPainterById: @Composable (Int) -> Painter) {
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