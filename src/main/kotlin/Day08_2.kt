fun main() {
    val size = input8.indexOf('\n')

    // populate
    val grid = Array(size) { IntArray(size) }
    var index = 0
    for(y in 0 until size) {
        for(x in 0 until size) {
            grid[y][x] = input8[index].digitToInt()
            index++
        }
        index++
    }

    // scan inner
    val found = mutableSetOf<Pair<Int, Int>>()
    for (y in 0 until size) {
        for (x in 0 until size) {
            var foundRight = true
            var foundLeft = true
            var foundBottom = true
            var foundTop = true
            for (i in 1..size) {
                if (x + i in 0 until size && grid[y][x + i] >= grid[y][x]) {
                    foundRight = false
                }
                if (x - i in 0 until size && grid[y][x - i] >= grid[y][x]) {
                    foundLeft = false
                }
                if (y + i in 0 until size && grid[y + i][x] >= grid[y][x]) {
                    foundBottom = false
                }
                if (y - i in 0 until size && grid[y - i][x] >= grid[y][x]) {
                    foundTop = false
                }
            }
            if (foundLeft || foundRight || foundTop || foundBottom) {
                found.add(Pair(y, x))
            }
        }
    }

    println(found.map { (y, x) ->
        var foundRight = 0
        var foundLeft = 0
        var foundBottom = 0
        var foundTop = 0
        for (i in 1..size) {
            if (x + i in 0 until size && foundRight == 0 && grid[y][x + i] >= grid[y][x]) {
                foundRight = i
            }
            if (x - i in 0 until size && foundLeft == 0 && grid[y][x - i] >= grid[y][x]) {
                foundLeft = i
            }
            if (y + i in 0 until size && foundBottom == 0 && grid[y + i][x] >= grid[y][x]) {
                foundBottom = i
            }
            if (y - i in 0 .. size && foundTop == 0 && grid[y - i][x] >= grid[y][x]) {
                foundTop = i
            }
        }
        foundRight = if (foundRight == 0 && x != size - 1) size - x - 1 else foundRight
        foundLeft = if (foundLeft == 0 && x != 0) x else foundLeft
        foundTop = if (foundTop == 0 && y != 0) y else foundTop
        foundBottom = if (foundBottom == 0 && y != size - 1) size - y - 1 else foundBottom
        foundRight * foundLeft * foundBottom * foundTop
    }.maxOf { it })
}