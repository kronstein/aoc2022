class TranslateFace(
    val y: IntRange,
    val x: IntRange,
    val facing: Int,
    val translate: (x: Int, y: Int) -> XY
) {
    operator fun invoke(x: Int, y: Int): XY = translate(x - this.x.first, y - this.y.first).also {
        println("Previous value: x=$x y=$y")
    }
}

data class XY(
    val x: Int,
    val y: Int,
    val facing: Int,
)

fun main() {
    val config = listOf<TranslateFace>(
        TranslateFace(y = 0..0, x = 51..100, facing = 3) { x, y -> // ok
            XY(x = 1, y = 151 + x, facing = 0).also {
                println("Translating from face 1 to 6 ($x, $y) -> (${it.x}, ${it.y}), 90 degrees clockwise")
            }
        },
        TranslateFace(y = 151..200, x = 0..0, facing = 2) {x, y -> // ok
            XY(x = 51 + y, y = 1, facing = 1).also {
                println("Translating from face 6 to 1 ($x, $y) -> (${it.x}, ${it.y}), 90 degrees counter-clockwise")
            }
        },
        TranslateFace(y = 51..100, x = 50..50, facing = 2) { x, y -> // ok
            XY(x = 1 + y, y = 101, facing = 1).also {
                println("Translating from face 3 to 5 ($x, $y) -> (${it.x}, ${it.y}), 90 counter-clockwise")
            }
        },
        TranslateFace(y = 100..100, x = 1..50, facing = 3) { x, y -> // ok
            XY(x = 51, y = 51 + x, facing = 0).also {
                println("Translating from face 5 to 3 ($x, $y) -> (${it.x}, ${it.y}), 90 clockwise")
            }
        },
        TranslateFace(y = 151..151, x = 51..101, facing = 1) { x, y -> // ok
            XY(x = 50, y = 151 + x, facing = 2).also {
                println("Translating from face 4 to 6 ($x, $y) -> (${it.x}, ${it.y}), 90 clockwise")
            }
        },
        TranslateFace(y = 151..200, x = 51..51, facing = 0) { x, y -> // ok
            XY(x = 51 + y, y = 150, facing = 3).also {
                println("Translating from face 6 to 4 ($x, $y) -> (${it.x}, ${it.y}), 90 counter-clockwise")
            }
        },
        TranslateFace(y = 51..100, x = 101..101, facing = 0) { x, y -> // ok
            XY(x = 101 + y, y = 50, facing = 3).also {
                println("Translating from face 3 to 2 ($x, $y) -> (${it.x}, ${it.y}), 90 counter-clockwise")
            }
        },
        TranslateFace(y = 51..51, x = 101..150, facing = 1) { x, y -> // ok
            XY(x = 100, y = 51 + x, facing = 2).also {
                println("Translating from face 2 to 3 ($x, $y) -> (${it.x}, ${it.y}), 90 clockwise")
            }
        },
        TranslateFace(y = 0..0, x = 101..150, facing = 3) { x, y -> // ok
            XY(x = 1 + x, y = 200, facing = 3).also {
                println("Translating from face 2 to 6 ($x, $y) -> (${it.x}, ${it.y}), 0 degrees")
            }
        },
        TranslateFace(y = 201..201, x = 1..50, facing = 1) { x, y -> // ok
            XY(x = 101 + x, y = 1, facing = 1).also {
                println("Translating from face 6 to 2 ($x, $y) -> (${it.x}, ${it.y}), 0 degrees")
            }
        },
        TranslateFace(y = 1..50, x = 151..151, facing = 0) { x, y -> // ok
            XY(x = 100, y = 100 + (50 - y), facing = 2).also {
                println("Translating from face 2 to 4 ($x, $y) -> (${it.x}, ${it.y}), 180 degrees")
            }
        },
        TranslateFace(y = 101..150, x = 101..101, facing = 0) { x, y -> // ok
            XY(x = 150, y = (50 - y), facing = 2).also {
                println("Translating from face 4 to 2 ($x, $y) -> (${it.x}, ${it.y}), 180 degrees")
            }
        },
        TranslateFace(y = 1..50, x = 50..50, facing = 2) { x, y -> // ok
            XY(x = 1, y = 100 + (50 - y), facing = 0).also {
                println("Translating from face 1 to 5 ($x, $y) -> (${it.x}, ${it.y}), 180 degrees")
            }
        },
        TranslateFace(y = 101..150, x = 0..0, facing = 2) {x, y -> // ok
            XY(x = 51, y = (50 - y), facing = 0).also {
                println("Translating from face 5 to 1 ($x, $y) -> (${it.x}, ${it.y}), 180 degrees")
            }
        },
    )

    val (input, path) = input22.split("\n").filter { it.isNotEmpty() }.let { i ->
        val height = i.size - 1
        val width = i.filterIndexed { index, _ -> index < height }.maxOf { it.length }
        i.take(height).map { line ->
            line + " ".repeat(width - line.length)
        } to i.last()
    }

    var pos = Current(
        x = input.first().indexOfFirst { it == '.' } + 1,
        y = 1,
        facing = 0,
        remainingPath = path,
    )

    val draw = input22.split("\n").map {
        StringBuilder(it)
    }
    draw[pos.y - 1][pos.x - 1] = '>'

    while(pos.remainingPath.isNotEmpty()) {
        pos = "^(\\d+)".toRegex().find(pos.remainingPath)?.let {
            val (steps) = it.destructured
            val nextRemainingPath = pos.remainingPath.substring(steps.length)

            for (i in 1..steps.toInt()) {
                var nextX = pos.x
                var nextY = pos.y
                var nextFacing = pos.facing

                do {
                    when (pos.facing) {
                        0 -> { // right
                            nextX += 1
                        }
                        1 -> { // down
                            nextY += 1
                        }
                        2 -> { // left
                            nextX -= 1
                        }
                        3 -> { // up
                            nextY -= 1
                        }
                    }

                    if (nextY < 1 || nextY > input.size || nextX < 1 || nextX > input[nextY - 1].length || input[nextY - 1][nextX - 1] == ' ') {
                        val conf = config.find { conf -> nextX in conf.x && nextY in conf.y && pos.facing == conf.facing }

                        conf?.invoke(nextX, nextY)?.let { xy ->
                            nextX = xy.x
                            nextY = xy.y
                            nextFacing = xy.facing
                        } ?: draw.forEach { o ->
                            println(o)
                        }.also {
                            error("Not supported y=$nextY x=$nextX facing=${pos.facing}")
                        }
                    }
                } while (input[nextY - 1][nextX - 1] == ' ')

                pos = if (input[nextY - 1][nextX - 1] == '.') {
                    pos.copy(
                        x = nextX,
                        y = nextY,
                        remainingPath = nextRemainingPath,
                        facing = nextFacing,
                    )
                } else {
                    pos.copy(
                        remainingPath = nextRemainingPath,
                    )
                }.also { c ->
                    draw[c.y - 1][c.x - 1] = when (c.facing) {
                        0 -> '>'
                        1 -> 'v'
                        2 -> '<'
                        3 -> '^'
                        else -> draw[nextY - 1][nextX - 1]
                    }
                }
            }
            pos
        } ?: when(pos.remainingPath[0]) {
            'R' -> { // clockwise
                pos.copy(
                    facing = (pos.facing + 1) % 4,
                    remainingPath = pos.remainingPath.substring(1)
                )
            }
            'L' -> { // counter-clockwise
                pos.copy(
                    facing = (4 + pos.facing - 1) % 4,
                    remainingPath = pos.remainingPath.substring(1)
                )
            }
            else -> error("Unexpected: ${pos.remainingPath}")
        }
    }

    draw.forEach { o ->
//        println(o)
    }

    println("${1000 * pos.y + 4 * pos.x + pos.facing}")

}