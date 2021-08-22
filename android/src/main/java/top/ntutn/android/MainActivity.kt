package top.ntutn.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.res.painterResource
import top.ntutn.common.App

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(MahjongAndroid.all.size) {
                painterResource(MahjongAndroid.all[it])
            }
        }
    }
}