package top.ntutn.common

fun <T> Array<Array<T>>.getByPair(pair: Pair<Int, Int>) = this[pair.first][pair.second]