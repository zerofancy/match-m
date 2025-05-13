package top.ntutn.match

import zmatch.composeapp.generated.resources.Res
import zmatch.composeapp.generated.resources.bamboo1
import zmatch.composeapp.generated.resources.bamboo2
import zmatch.composeapp.generated.resources.bamboo3
import zmatch.composeapp.generated.resources.bamboo4
import zmatch.composeapp.generated.resources.bamboo5
import zmatch.composeapp.generated.resources.bamboo6
import zmatch.composeapp.generated.resources.bamboo7
import zmatch.composeapp.generated.resources.bamboo8
import zmatch.composeapp.generated.resources.bamboo9
import zmatch.composeapp.generated.resources.dragon_chun
import zmatch.composeapp.generated.resources.dragon_green
import zmatch.composeapp.generated.resources.dragon_haku
import zmatch.composeapp.generated.resources.face_down
import zmatch.composeapp.generated.resources.man1
import zmatch.composeapp.generated.resources.man2
import zmatch.composeapp.generated.resources.man3
import zmatch.composeapp.generated.resources.man4
import zmatch.composeapp.generated.resources.man5
import zmatch.composeapp.generated.resources.man6
import zmatch.composeapp.generated.resources.man7
import zmatch.composeapp.generated.resources.man8
import zmatch.composeapp.generated.resources.man9
import zmatch.composeapp.generated.resources.pin1
import zmatch.composeapp.generated.resources.pin2
import zmatch.composeapp.generated.resources.pin3
import zmatch.composeapp.generated.resources.pin4
import zmatch.composeapp.generated.resources.pin5
import zmatch.composeapp.generated.resources.pin6
import zmatch.composeapp.generated.resources.pin7
import zmatch.composeapp.generated.resources.pin8
import zmatch.composeapp.generated.resources.pin9
import zmatch.composeapp.generated.resources.red_dora_bamboo5
import zmatch.composeapp.generated.resources.red_dora_man5
import zmatch.composeapp.generated.resources.red_dora_pin5
import zmatch.composeapp.generated.resources.wind_east
import zmatch.composeapp.generated.resources.wind_north
import zmatch.composeapp.generated.resources.wind_south
import zmatch.composeapp.generated.resources.wind_west

/**
 * 麻将牌
 */
object MahjongRes {
    val bamboos = listOf(
        Res.drawable.bamboo1,
        Res.drawable.bamboo2,
        Res.drawable.bamboo3,
        Res.drawable.bamboo4,
        Res.drawable.bamboo5,
        Res.drawable.bamboo6,
        Res.drawable.bamboo7,
        Res.drawable.bamboo8,
        Res.drawable.bamboo9
    )
    val dragons = listOf(
        Res.drawable.dragon_chun,
        Res.drawable.dragon_green,
        Res.drawable.dragon_haku
    )
    val faceDown = Res.drawable.face_down
    val mans = listOf(
        Res.drawable.man1,
        Res.drawable.man2,
        Res.drawable.man3,
        Res.drawable.man4,
        Res.drawable.man5,
        Res.drawable.man6,
        Res.drawable.man7,
        Res.drawable.man8,
        Res.drawable.man9
    )
    val pins = listOf(
        Res.drawable.pin1,
        Res.drawable.pin2,
        Res.drawable.pin3,
        Res.drawable.pin4,
        Res.drawable.pin5,
        Res.drawable.pin6,
        Res.drawable.pin7,
        Res.drawable.pin8,
        Res.drawable.pin9
    )
    val redDoras = listOf(
        Res.drawable.red_dora_bamboo5,
        Res.drawable.red_dora_man5,
        Res.drawable.red_dora_pin5
    )
    val winds = listOf(
        Res.drawable.wind_east,
        Res.drawable.wind_north,
        Res.drawable.wind_south,
        Res.drawable.wind_west
    )
    val front = bamboos + dragons + mans + pins + redDoras + winds
    val all = front + faceDown
}