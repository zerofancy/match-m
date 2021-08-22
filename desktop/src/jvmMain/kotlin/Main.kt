import androidx.compose.desktop.Window
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.graphics.painter.BitmapPainter
import top.ntutn.common.App

fun main() = Window {
    App(resourceItemCount = MahjongDesktop.all.size) {
        BitmapPainter(imageFromResource("drawable/${MahjongDesktop.all[it]}.png"))
    }
}