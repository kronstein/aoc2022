import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class SensorBeacon(
    val sx: Int,
    val sy: Int,
    val bx: Int,
    val by: Int,
) {
    fun scanRow(row: Int): Set<Int> {
        val result = mutableSetOf<Int>()
        for(x in sx - distance .. sx + distance) {
            if (abs(sy - row) + abs(sx - x) <= distance) {
                result.add(x)
            }
        }
        return result
    }

    fun isInside(x: Int, y: Int): Boolean = abs(sy - y) + abs(sx - x) <= distance

    inline fun scanBounds(onBound: (Int, Int) -> Unit) {
        for(c in 0..distance) {
            val d = distance - c
            val x1 = sx + c
            val y1 = sy + d + 1
            val y2 = sy - d - 1
            onBound(x1, y1)
            onBound(x1, y2)
        }
        onBound(sx - distance - 1, sy)
        onBound(sx + distance + 1, sy)
    }

    val distance by lazy { abs(sy - by) + abs(sx - bx) }
}

fun main() {
    val sensors = input15.split("\n").map { line ->
        val (sx, sy, bx, by) = "Sensor at x=(.?\\d+), y=(.?\\d+): closest beacon is at x=(.?\\d+), y=(.?\\d+)".toRegex().find(line)!!.destructured
        SensorBeacon(sx.toInt(), sy.toInt(), bx.toInt(), by.toInt())
    }

    val row = 2000000
    val common = mutableSetOf<Int>()
    sensors.forEach {
        common.addAll(it.scanRow(row))
    }
    sensors.filter { it.by == row }.forEach {
        common.remove(it.bx)
    }
    sensors.filter { it.sy == row }.forEach {
        common.remove(it.sx)
    }

    println(common.size)
}

val input15 = """
Sensor at x=2983166, y=2813277: closest beacon is at x=3152133, y=2932891
Sensor at x=2507490, y=122751: closest beacon is at x=1515109, y=970092
Sensor at x=3273116, y=2510538: closest beacon is at x=3152133, y=2932891
Sensor at x=1429671, y=995389: closest beacon is at x=1515109, y=970092
Sensor at x=2465994, y=2260162: closest beacon is at x=2734551, y=2960647
Sensor at x=2926899, y=3191882: closest beacon is at x=2734551, y=2960647
Sensor at x=1022491, y=1021177: closest beacon is at x=1515109, y=970092
Sensor at x=1353273, y=1130973: closest beacon is at x=1515109, y=970092
Sensor at x=1565476, y=2081049: closest beacon is at x=1597979, y=2000000
Sensor at x=1841125, y=1893566: closest beacon is at x=1597979, y=2000000
Sensor at x=99988, y=71317: closest beacon is at x=86583, y=-1649857
Sensor at x=3080600, y=3984582: closest beacon is at x=3175561, y=4138060
Sensor at x=3942770, y=3002123: closest beacon is at x=3724687, y=3294321
Sensor at x=1572920, y=2031447: closest beacon is at x=1597979, y=2000000
Sensor at x=218329, y=1882777: closest beacon is at x=1597979, y=2000000
Sensor at x=1401723, y=1460526: closest beacon is at x=1515109, y=970092
Sensor at x=2114094, y=985978: closest beacon is at x=1515109, y=970092
Sensor at x=3358586, y=3171857: closest beacon is at x=3152133, y=2932891
Sensor at x=1226284, y=3662922: closest beacon is at x=2514367, y=3218259
Sensor at x=3486366, y=3717867: closest beacon is at x=3724687, y=3294321
Sensor at x=1271873, y=831354: closest beacon is at x=1515109, y=970092
Sensor at x=3568311, y=1566400: closest beacon is at x=3152133, y=2932891
Sensor at x=3831960, y=3146611: closest beacon is at x=3724687, y=3294321
Sensor at x=2505534, y=3196726: closest beacon is at x=2514367, y=3218259
Sensor at x=2736967, y=3632098: closest beacon is at x=2514367, y=3218259
Sensor at x=3963402, y=3944423: closest beacon is at x=3724687, y=3294321
Sensor at x=1483115, y=2119639: closest beacon is at x=1597979, y=2000000
""".trimIndent()
