fun main() {
    val columns = input12.indexOf('\n')
    val rows = input12.split("\n").size

    // populate
    val grid = Array(rows) { Array(columns) { Point() } }
    var index = 0
    for (y in 0 until rows) {
        for (x in 0 until columns) {
            grid[y][x] = when(input12[index]) {
                'S' -> Point(height = 'a', start = true)
                'E' -> Point(height = 'z', end = true)
                else -> Point(height = input12[index])
            }
            index++
        }
        index++
    }

    // convert
    val graph = mutableListOf<Point>()
    for (y in 0 until rows) {
        for(x in 0 until columns) {
            val point = grid[y][x]
            if (y - 1 in 0 until rows) {
                point.connections.add(grid[y - 1][x])
            }
            if (y + 1 in 0 until rows) {
                point.connections.add(grid[y + 1][x])
            }
            if (x - 1 in 0 until columns) {
                point.connections.add(grid[y][x - 1])
            }
            if (x + 1 in 0 until columns) {
                point.connections.add(grid[y][x + 1])
            }

            graph.add(point)
        }
    }

    // find
    val starts = graph.filter { it.height == 'a' }
    val end = graph.first { it.end }
    println(starts.map {
        graph.forEach { point -> point.visited = point.height == 'a' }
        go(
            count = 1,
            end = end,
            test = it.accessibleConnections
        )
    }.minByOrNull { it })
}
