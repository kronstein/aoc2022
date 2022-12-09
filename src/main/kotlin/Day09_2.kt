import kotlin.math.abs

fun main() {
    val rope = arrayOf(Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0))
    val visited = mutableSetOf(rope.last())

    input9.split("\n").forEach {
        val dir = it[0]
        val steps = it.substring(2).toInt()

        for (i in 1..steps) {
            when (dir) {
                'U' -> rope[0] = Pair(rope[0].first + 1, rope[0].second)
                'D' -> rope[0] = Pair(rope[0].first - 1, rope[0].second)
                'L' -> rope[0] = Pair(rope[0].first, rope[0].second - 1)
                'R' -> rope[0] = Pair(rope[0].first, rope[0].second + 1)
            }
            for(j in 1 until rope.size) {
                val distanceY = abs(rope[j - 1].first - rope[j].first)
                val distanceX = abs(rope[j - 1].second - rope[j].second)

                when {
                    distanceY <= 1 && distanceX <= 1 -> {}
                    distanceY == 0 -> rope[j] = Pair(rope[j].first, rope[j].second + if (rope[j - 1].second > rope[j].second) 1 else -1)
                    distanceX == 0 -> rope[j] = Pair(rope[j].first + if (rope[j - 1].first > rope[j].first) 1 else -1, rope[j].second)
                    else -> rope[j] = Pair(
                        rope[j].first + if (rope[j - 1].first > rope[j].first) 1 else -1,
                        rope[j].second + if (rope[j - 1].second > rope[j].second) 1 else -1
                    )
                }
            }

            visited.add(rope.last())
        }
    }

    println(visited.size)
}