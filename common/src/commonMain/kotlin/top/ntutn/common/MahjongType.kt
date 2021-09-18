package top.ntutn.common

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