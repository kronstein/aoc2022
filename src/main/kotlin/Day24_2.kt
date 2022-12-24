fun main() {
    val blizzards = input24.split("\n").flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
            when(c) {
                '>' -> Blizzard(x = x, y = y, dx = 1, dy = 0)
                '<' -> Blizzard(x = x, y = y, dx = -1, dy = 0)
                'v' -> Blizzard(x = x, y = y, dx = 0, dy = 1)
                '^' -> Blizzard(x = x, y = y, dx = 0, dy = -1)
                else -> null
            }
        }
    }

    println(5.adjust(min = 1, max = 4))

    val bounds = Bounds(
        left = 1,
        right = input24.indexOf("\n") - 2,
        top = 1,
        bottom = input24.split("\n").size - 2,
    )

    val start = Position(x = 1, y = 0)
    val end = Position(x = bounds.right, y = bounds.bottom + 1)

    println("bounds: $bounds start: $start end: $end")

    val goFromStartToEnd1 = search(0, blizzards, setOf(start), bounds, end)
    val goFromEndToStart = search(0, goFromStartToEnd1.blizzards, setOf(end), bounds, start)
    val goFromStartToEnd2 = search(0, goFromEndToStart.blizzards, setOf(start), bounds, end)

    println(goFromStartToEnd1.minute + goFromEndToStart.minute + goFromStartToEnd2.minute)
}