fun main() {
    val cave = mutableMapOf<Pair<Int, Int>, Cave>()
    var maxY: Int = Int.MIN_VALUE

    input14.split("\n").map { line ->
        line.split("->").map { it.trim() }.map {
            val (x, y) = "(\\d+),(\\d+)".toRegex().find(it)!!.destructured
            x.toInt() to y.toInt()
        }
    }.forEach { rockFormation ->
        rockFormation.zipWithNext().forEach {
            val x1 = Integer.min(it.first.first, it.second.first)
            val x2 = Integer.max(it.first.first, it.second.first)

            val y1 = Integer.min(it.first.second, it.second.second)
            val y2 = Integer.max(it.first.second, it.second.second)

            for(y in y1 .. y2) {
                maxY = Integer.max(maxY, y)
                for(x in x1..x2) {
                    cave[x to y] = Cave.Rock
                }
            }
        }
    }

    maxY += 2

    while(true) {
        var activeSand = 500 to 0
        if (cave[activeSand] != null) {
            break
        }
        while(activeSand.second < maxY - 1) {
            val d = activeSand.first to activeSand.second + 1
            if (cave[d] == null) {
                activeSand = d
                continue
            }
            val dl = activeSand.first - 1 to activeSand.second + 1
            if (cave[dl] == null) {
                activeSand = dl
                continue
            }
            val dr = activeSand.first + 1 to activeSand.second + 1
            if (cave[dr] == null) {
                activeSand = dr
                continue
            }
            break
        }
        cave[activeSand] = Cave.Sand
    }

    println(cave.count { it.value is Cave.Sand })
}