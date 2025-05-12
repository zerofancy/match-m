package top.ntutn.match

object MahjongDesktop {
    val bamboos = listOf(
        "bamboo1",
        "bamboo2",
        "bamboo3",
        "bamboo4",
        "bamboo5",
        "bamboo6",
        "bamboo7",
        "bamboo8",
        "bamboo9"
    )
    val dragons = listOf(
        "dragon_chun",
        "dragon_green",
        "dragon_haku"
    )
    val faceDown = "face_down"
    val mans = listOf(
        "man1",
        "man2",
        "man3",
        "man4",
        "man5",
        "man6",
        "man7",
        "man8",
        "man9"
    )
    val pins = listOf(
        "pin1",
        "pin2",
        "pin3",
        "pin4",
        "pin5",
        "pin6",
        "pin7",
        "pin8",
        "pin9"
    )
    val redDoras = listOf(
        "red_dora_bamboo5",
        "red_dora_man5",
        "red_dora_pin5"
    )
    val winds = listOf(
        "wind_east",
        "wind_north",
        "wind_south",
        "wind_west"
    )
    val front = bamboos + dragons + mans + pins + redDoras + winds
    val all = front + faceDown
}