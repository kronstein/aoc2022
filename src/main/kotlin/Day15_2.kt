fun main() {
    val sensors = input15.split("\n").map { line ->
        val (sx, sy, bx, by) = "Sensor at x=(.?\\d+), y=(.?\\d+): closest beacon is at x=(.?\\d+), y=(.?\\d+)".toRegex().find(line)!!.destructured
        SensorBeacon(sx.toInt(), sy.toInt(), bx.toInt(), by.toInt())
    }

    sensors.forEach { sensor ->
        val weaker = sensors.filter { it != sensor }
        sensor.scanBounds { x, y ->
            if (x in 0..4000000 && y in 0..4000000) {
                var found = false
                for (w in weaker) {
                    if (w.isInside(x, y)) {
                        found = true
                        break
                    }
                }
                if (!found) {
                    println("${x.toLong() * 4000000 + y}")
                    return
                }
            }
        }
    }
}
