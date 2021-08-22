package top.ntutn.android

/**
 * 麻将牌
 */
object MahjongAndroid {
    val bamboos = listOf(
        R.drawable.bamboo1,
        R.drawable.bamboo2,
        R.drawable.bamboo3,
        R.drawable.bamboo4,
        R.drawable.bamboo5,
        R.drawable.bamboo6,
        R.drawable.bamboo7,
        R.drawable.bamboo8,
        R.drawable.bamboo9
    )
    val dragons = listOf(
        R.drawable.dragon_chun,
        R.drawable.dragon_green,
        R.drawable.dragon_haku
    )
    val faceDown = R.drawable.face_down
    val mans = listOf(
        R.drawable.man1,
        R.drawable.man2,
        R.drawable.man3,
        R.drawable.man4,
        R.drawable.man5,
        R.drawable.man6,
        R.drawable.man7,
        R.drawable.man8,
        R.drawable.man9
    )
    val pins = listOf(
        R.drawable.pin1,
        R.drawable.pin2,
        R.drawable.pin3,
        R.drawable.pin4,
        R.drawable.pin5,
        R.drawable.pin6,
        R.drawable.pin7,
        R.drawable.pin8,
        R.drawable.pin9
    )
    val redDoras = listOf(
        R.drawable.red_dora_bamboo5,
        R.drawable.red_dora_man5,
        R.drawable.red_dora_pin5
    )
    val winds = listOf(
        R.drawable.wind_east,
        R.drawable.wind_north,
        R.drawable.wind_south,
        R.drawable.wind_west
    )
    val front = bamboos + dragons + mans + pins + redDoras + winds
    val all = front + faceDown
}

/**
 * 麻将数据类型
 * @param id 本次游戏中的编号（不是资源id）
 * @param isSelected 麻将是否被选中
 * @param isDeleted 麻将是否已经被删除
 */
data class MahjongType(
    val id: Int,
    var isSelected: Boolean = false,
    var isDeleted: Boolean = false
)