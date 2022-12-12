class Point(
    val height: Char = 'a',
    val connections: MutableList<Point> = mutableListOf(),
    val start: Boolean = false,
    val end: Boolean = false,
    var visited: Boolean = false,
) {
    private fun isAccessible(other: Point): Boolean = other.height <= height || other.height - height == 1

    val accessibleConnections
        get() = connections.filter { !it.visited && isAccessible(it) }

    override fun toString(): String {
        return when {
            start -> "S"
            end -> "E"
            else -> "$height"
        }
    }
}

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
    val start = graph.first { it.start }.also {
        it.visited
    }
    val end = graph.first { it.end }
    go(
        count = 1,
        end = end,
        test = start.accessibleConnections
    ).also {
        println("$it")
    }
}


fun go(
    count: Int,
    test: List<Point>,
    end: Point,
): Int {
    test.forEach {
        it.visited = true
        if (it == end) {
            return count
        }
    }
    val nextTest = test.flatMap { it.accessibleConnections }.distinct()
    if (nextTest.isEmpty()) {
        return Int.MAX_VALUE
    }
    return go(count + 1, nextTest, end)
}

val input12 = """
abcccccccccccccccccaaccccccccccccaaaaaaaacccccccccccaaaaaccccaaaaaaccaaaaaaaaaaaaaaaaaccccccccccccccccaaacccccaaaaaaaacccaaaccccccccccccccccccccccccccccccccccccccccccccccccccaaaaa
abccccccccccccccccaaacaacccccccccccaaaacccccccccccccaaaaaacccaaaaaaccaaaaaaaaaaaaaaaaaaaacccccccccaaacaaacccccaaaaaaaaaccaaaaccccccccccccccccccccccccccccccccccccccccccccccccaaaaaa
abcccccccccccccccccaaaaacccccccccccaaaaaccccccccccccaaaaaaccccaaaacccaaaacccaaaaaaaaaaaaacccccccccaaaaaaaaaacccaaaaaaaaccaaaaccccccccccccccccccccccccccccccccccaaacccccccccccaaaaaa
abcccccccccccccccaaaaaacccccccccccaaacaaccaaccccccccaaaaaaccccaaaacccaaaccccaaaaaaaaaaaaaccccccccccaaaaaaaaaccaaaaaacccccaaacccccccccccccccccccccccccccccccccccaaaccccccccccccccaaa
abcccccccccccccccaaaaaaaacccccccccaacccacaaacaacccccccaaccccccaccaccccccccaaaaaaaaaaaaccccccccccccccaaaaaaacccaaaaaaacccccccccccccaacccccccccccccccccccccccccccaaaccccccccccccccaaa
abcccccccccccccccaacaaaaacccccccccccccccccaaaaacccccccccccccccccccccccccccaaaaaaaaaaaaccccccccccccccaaaaaaccccaaccaaacccccccccccaaaaaaccccccccccccccccccccccccccdccccccccccccccccaa
abccaacccccccaaacccaaacaccccccccccaaacccaaaaaaccccccccccccccccccccccccccccaaacccaaaaaacaaaaccccccccaaaaaaaccccccccaaacccccccccccaaaaaacccccccccccccccccllllllcccdddddcccccccccccccc
abaaaacccacccaaccccaacccccccccccccaaacccaaaaaaaacccccccccccccccccaaccccccccacccaaaaccccaaaaccccccccaaacaaaccccccccccccccccccccccaaaaaaccccccccccccccccllllllllldddddddddddccaaccccc
abaaaaccaaaaaaaacccccccccccccccaaaaaaaacaacaaaaacccccccccccccccccaaacccccccaaacaaccccccaaaacccccccccccccaaccccccccccccccccccccccaaaaaccccccccccccccccclllllllllldddddddddeeaaaccccc
abaaaccccaaaaaaaaccccccccaaacccaaaaaaaacccaaacccccccccccccccccaaaaaaaaccccccaaaaacccccccaacccccccccccccccccccccccccccccccccccccccaaaaccccaaaccccccccckllppppplllmmmmmmmdeeeeaaccccc
abaaaacccaaaaaaaaacccccaaaaaaccccaaaaaccccaaccccccccccccccccccaaaaaaaaccccccaaaaaaacccccccccccccccccccccccccccccccccccccccccccccccccccccaaaacccccccckklpppppppplmmmmmmmmmeeeeaccccc
abaaaacccaaaaaaaaacccccaaaaaacccaaaaaaccccccccaacccccccccccccccaaaaaaccccccaaaaaaaacccccccccccccccccccccccccccccccccccccccccccccccccccccaaaacccccccckkkppppppppqmmmmmmmmmmeeeaacccc
abaaaaaccaaaaaaaaccccccaaaaaacccaaaaaaccccccacaaaacccccccccccccaaaaaaccccccaaaaaaaaccccccccaaaaccccccccaaacccccccccccccccccccccccccccccccaaacccccccckkkpppuuuppqqqqqqqqmmmeeeeacccc
abacccccaaaaaaaccccccccaaaaaccccaccaaaccccccaaaaaacccccacccccccaaaaaaccccccaaaaaacaaaccccccaaaaccccccccaaaacccccccccccccccccccccccccccccccccccccccckkkpppuuuuuuqqqqqqqqqnnneeeccccc
abcccccccaccaaaccccccccaaaaacccccccccccccccccaaaacccaaaacccccccaacaaacccccccccaaacaaaaaccccaaaaccccccccaaaaccccccccccccccccccccccccccccccccccccccckkkkpppuuuuuuuqvvvvqqqnnneeeccccc
abcccccccccccaaacaaccccccccccccccccccccccccccaaaacccaaaaaacccccccccccccccccccccccaaaaaaccccaaacccccccccaaaccccccccccccccccccccccccccccccccccccccckkkkrrpuuuxxxuvvvvvvvqqnnneeeccccc
abcccccccccccccccaacaaaccccccccccccccccccccccaacaacccaaaaacccccccccccccccccccccccaaaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccckkkkrrrruuxxxxuvvvvvvvqqnnneeeccccc
abcccccccccccccccaaaaacccccccccccccccccccccccccccaccaaaaacccccaaccccccccccccccccccaaaaaccccccccccccccccaaaccccccccccccccaaacccccccccccccccccccckkkkrrrruuuxxxxyyyyyvvvqqnnneecccccc
abcccccccccccccaaaaaacccccccccccaacaacccccccccccaaaaaaaaacccacaaaacaaccaaccccccccaaacaaccccccccccaaccccaaaaaacccaacaacccaaacacccccccccccccccccjjkkrrrruuuuuxxxyyyyyvvqrqnneffcccccc
abcccccccccccccaaaaaaaacccaaacccaaaaaccccccaacccaaaaaaaaacccaaaaaacaaaaaaccccccccaaacaaacccccccaaaaaacaaaaaaacccaaaaacaaaaaaaaccccccccccccccccjjjrrrtttuuxxxxxyyyyyvvrrnnnfffcccccc
SbccccccccccccccccaaaaacccaaaaccaaaaaaccccaaaaaacaaaaaaaaacccaaaacccaaaaaccccccccaaaaaaacccccccaaaaaaaaaaaaaaccaaaaaccaaaaaaaaccccccccccccccccjjjrrrtttxxxEzzzzyyyvvrrrnnnfffcccccc
abccccccccccaaaccaaccaacccaaaaccaaaaaacccccaaaaacaaaaaaaaacccaaaaccaaaaaacccccccccaaaaaaccccccccaaaacaaaaaaacccaaaaaaccaaaaaacccccccccccccccccjjjrrrtttxxxxxyyyyyyvvrrrnnnfffcccccc
abcccccccccaaaaccaacccccccaaacccaaaaaacccaaaaaaaaaaaaaaaaacccaacacaaaaaaaacccccaaaaaaaacccccccccaaaacccaaaaaaccccaaaacccaaaaacccccccccccccccccjjjrrrtttxxxxxyyyyyyywvrrnnnfffcccccc
abcccccccccaaaacccccccccccccccccccaaaccccaaaaaaaaaaaaaaaaaacccccccaaaaaaaacccccaaaaaaaaaccccccccaccacccaaaaaaccccacccccaaaaaacccccccccccccccccjjjrrrrttttxxxyyyyyyywwrrroooffcccccc
abccccccccccaaaccccccccccccccccccccccccccaaaaaaaaaccaaaaaaaccccccccccaaccccccccaaaaaaaaaaccccccccccccccaacccccccccccccccaaccccccccccccccccccccjjjjqqqqttttxxyywwwwwwwwrrooofffccccc
abcccccccccccccccccccccccccccccccccccccccccaaacacccccaaaaccccccccccccaacccccccccccaaacaaacccccccccccccccccccccccccccccaaacccccccccccccccccccaacjjjjqqqqqttwwwwwwwwwwwrrrooofffccccc
abcccccccccccccccccccccccccccccccccccccccccaaccccccccccaacccccccccccccccccccccccccaaacccccccccccccccccccccccccccccccccaaacccccccccccccccccaaaaaajjjjqqqqttwwwwwwsswwrrrrooofffccccc
abcccccaaaaccccccccccccccaacaaccccccccccccccccccccccccccccccccccccccccccccccccccccaacccccccccccccccccccccccccccccccaaaaaaaaccaaaacccccccccaaaaaacjjjiqqqtttwwwwsssssrrrrooofffccccc
abccccaaaaaccccccccccccccaaaaacccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaaaaaccaaaaacccccccccaaaaacciiiiqqttswwwssssssrrroooogffccccc
abccccaaaaaacccccccccccccaaaaaacccaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaaccaaaaaaccccccccaaaaacccciiiqqqssssssspppooooooogggaccccc
abccccaaaaaaccccccccaacccaaaaaaccccaacccccccccccccccccccccccccccccccccaaaaccccccccccccccccccccccccccccccccccccccccccaaaaaaccaaaaaaccccccccaaaaaccccciiiqqsssssspppppoooooggggaacccc
abccccaaaaaccccccaaaaacccaaaaaacaacaaaaaccccccccccccccccccccaacccccccaaaaacccccccccccaaaccccccccccccccccccccccccccccaaaaaacccaaaaacccccccccacccccccciiiqqqpssspppppgggggggggaaacccc
abccccccaaacccccccaaaaaccccaaaccaaaaaaaacccccccaacccccccaaccaacccccccaaaaaacccccccccaaaacccccccccccaaaaccccccccccccaaccaaacccaaacccccaaacaaaccccccccciiqqppppppphhhggggggggaaaacccc
abccccccccccccccccaaaaaccccccccccaaaaaccccccccaaacaaccccaaaaaacccccccaaaaaaaccccccccaaaacccccccccccaaaacccccccccaaaaaacccccccccccccccaaaaaaaccccccccciiippppppphhhhggggggcaaacccccc
abaacccccccccccccaaaaaccccccccccccaaaaaccccccccaaaaacccccaaaaaaacccccaaaaacaaacccccccaaacccccccccccaaaacccccccccaaaaacccccccccccccccccaaaaaacccccccccciiiippphhhhhhcccccccaaacccccc
abaacccccccccccccccaaacccccccccccaaacaaccccccaaaaaaccccccaaaaaaacccaaccaaacaaaaccaaaccccccccccccccccaaacccccccccaaaaaaacccccccccccccccaaaaaaaacccccccciiihhhhhhhhaaaccccccccccccccc
abaaccccccccccccccccccccccccccccccaacccccccccaaaaaaaacccaaaaaaccaaaaaacccccaaaacaaaaaccccccccccccccccccccccccccaaaaaaaaccccccccccccccaaaaaaaaaccccccccciihhhhhhcaaaacccccccccccccca
abaaccccccccccccccccccccccccccccccccaacccccccaacaaaaacccaaaaaaccaaaaacccccccaaaaaaaacccccccccccccccccccccccccccaaaaaaaacccccccccaaacaaaaaaaaaacccccccccccchhhaccccaacccccccccccccca
abaaccccccccccccccccccccccccccccccccaaaaaaccccccaaccccccccccaaccaaaaaaacccccaaaaaaaccccccccccccccccccccccccaaaccacaaacccccccccccaaaaaaacaaacaaaaaacccccccccaaacccccccccccccccaaaaaa
abccccccccccccccccccccccccccccccccccaaaaaccccccaaccccccccccccccaaaaaaaacaaaaaaaaaaccccccccccccccccccccccccaaaaaaccaaaccccccccccccaaaaaacaaacaaaaaacccccccccaaaccccccccccccccccaaaaa
abccccccccccccccccccccccccccccccccaaaaaaaacccccccccccccccccccccaaaaaaaacaaaaaaaaaaaaacccccccccccccccccccccaaaaaacccccccccccccccaaaaaaaaaaaaaaaaaacccccccccccccccccccccccccccccaaaaa
""".trimIndent()
