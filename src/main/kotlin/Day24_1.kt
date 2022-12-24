data class Blizzard(
    val x: Int,
    val y: Int,
    val dx: Int,
    val dy: Int,
)

data class Position(
    val x: Int,
    val y: Int,
)

data class Bounds(
    val top: Int,
    val left: Int,
    val right: Int,
    val bottom: Int,
)

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

    println(search(0, blizzards, setOf(start), bounds, end))
}

fun search(
    minute: Int,
    blizzards: List<Blizzard>,
    test: Set<Position>,
    bounds: Bounds,
    end: Position,
): Int {
    println("\n === Minute #$minute ===")

    blizzards.draw(test, bounds)

    if (test.contains(end)) { // End of expedition is reached
        return minute
    }

    val nextTest = test.filter { pos -> blizzards.none { it.x == pos.x && it.y == pos.y } }.flatMap { pos ->
        pos.getAdjacent(bounds, end) + pos
    }.toSet()
    val nextState = blizzards.moveToState(bounds)

    return search(
        minute = minute + 1,
        blizzards = nextState,
        test = nextTest,
        bounds = bounds,
        end = end,
    )
}

fun List<Blizzard>.moveToState(bounds: Bounds): List<Blizzard> {
    return map { blizzard: Blizzard ->
        val x = (blizzard.x + blizzard.dx).adjust(min = bounds.left, max = bounds.right)
        val y = (blizzard.y + blizzard.dy).adjust(min = bounds.top, max = bounds.bottom)
        blizzard.copy(
            x = x,
            y = y,
        )
    }
}

fun Int.adjust(min: Int, max: Int): Int {
    var result = this
    if (result < min) result = max
    if (result > max) result = min
    return result
}

fun Position.getAdjacent(bounds: Bounds, end: Position): Set<Position> {
    return listOf(
        Position(x = x - 1, y = y), // left
        Position(x = x + 1, y = y), // right
        Position(x = x, y = y - 1), // up
        Position(x = x, y = y + 1), // down
    ).filter { pos ->
        pos == end || (pos.x >= bounds.left && pos.x <= bounds.right && pos.y >= bounds.top && pos.y <= bounds.bottom)
    }.toSet()
}

fun List<Blizzard>.draw(test: Set<Position>, bounds: Bounds) {
    for(y in bounds.top - 1..bounds.bottom + 1) {
        for(x in bounds.left - 1..bounds.right + 1) {
            if (x == bounds.left - 1 || x == bounds.right + 1 || y == bounds.top - 1 || y == bounds.bottom + 1) {
                print(if (test.any { it.x == x && it.y == y}) { '*' } else { '#' })
                continue
            }
            val first = this.firstOrNull { it.x == x && it.y == y }
            when(val count = this.count { it.x == x && it.y == y}) {
                0 -> print(if (test.any { it.x == x && it.y == y}) { '*' } else { '.' })
                1 -> print(when {
                    first?.dx == 1 && first.dy == 0 -> '>'
                    first?.dx == -1 && first.dy == 0 -> '<'
                    first?.dx == 0 && first.dy == -1 -> '^'
                    first?.dx == 0 && first.dy == 1 -> 'v'
                    else -> error("Unsupported: $first")
                })
                else -> print(count.toString()[0])
            }
        }
        println()
    }
}

val input24 = """
#.########################################################################################################################
#<<><>>v.<<<<.^><<<^^<><.v^vv>>^>.<.>v^<>>.<<v>v<><v>>^<v^^>.>^vv^<v>><^vv>>v><<.v<v<<^<<^v..>v<>v<<v<<><>>>^>>.v^>^vv>><#
#>^^<>.>v^^<.>>vv^v<v<.<v..v.><><><<v^<>^v>^^v<>>.vv<v>v<vvv>^<<>^<<<>v>>^^>^>^v<^vv^v<><>v^<<>^vvvvv<^>>>..>><>^^.>^^.v>#
#<v<>v<>.<<<v<^><<>vv<^>^>><v<v><^v^<^v.v^v<>.^>^<^vv<<>>^v<v<<>>^v^^v^v><<.>^^vv>>v>^>v.>>v^^>>><v^vv^v^v>^v><<>^<<v<<><#
#<<v^^<<><v^^v>v.><<^><^<^v<v^^^v^.<>>v.<<><^>vv^^<<>^>.v^<^<^<>v<v<v<v>vv^^>>v<.<^vv.><<.<^.v><^^<>><v<><^^^>.<^..<^<>^>#
#>>^>^<v><v^<v^v>vv<>>>>^v>>^<v.>^^<^<..>^vvv^v>^v>><^.>>^v^<v>>vv>>^^><.><<<^><vv><^^^<vv<v<^^<>>>vv>v>v<^^v^><<vv<.v.v<#
#>v<<.v<^><.>v^.<v<^>vvvvv<vv>>.>^.^>><^>v>>><^>v><>^<>>><v^vv<^^v^v>>>^v><^v.>^<v.<vv>^.>vv^v^^<<.<<>>^^<vv>..><vv.v^^<>#
#<^^^>^v<^><v^<>><^>^.^vvv<><vvvv^>^<>v^^vv<<>>^.v<>v>^<v<vv><<<^^>><^vvvv<>>>^>^^>^^<<^^>.vv^vv>v<<<.<^>v><^.<v<>v^.><^>#
#<>^>.>^<<><^v<><>^>>>.vv>^v<.><v^vv^<>v^v<^<>>><<..^>.v^v.vv<^<<<^.v>v.^^^>^><>><v^vv^<^v<>>>^.<<v^>>vv.^<<<^<<>.vv>^vv>#
#<>vvv>^v.^^>>^^^>.>v<>v^<<.<<<.vv.^^^^^v..v><^v^v<<>>vv<>>v^v^^.>>^<^<^^<^v>v<^>>^v<<^.><.vv>^^^^><<.^.v^v>v<>.v^v<v.<^<#
#>v^v>.v^<vv><<>v^>v<^<^>^<vvv.^.>>^^.^>^<>v<v^><^<^.^^v>^^.v.<^v>>^^>^<.^>^<.^..^<<<<^<<^>><.^<<^.<<^<<^v.<..v>v<vv^<.<<#
#>>.>^<>.>v>.^><^^v^v>.v^.>.v..>..<v.<>><v.vv<<vvv>^>>v^^^><<v><.^v>><^vv<>.>vv^v^^v.v^.v.^>^><.^<^^^>>>>^<<^^.vv><>>>>.>#
#>^>>^>vvvv<^>><^>><^v<<<<>v<.^v^^>vvvv^><<^v>^^>>v<^v><<>><>v^^>v>v^v>^..<<vv.>vv><^<<^v<>v<v^v<<<><<^>^^v.<^><v.><>..v>#
#>^^>v.^v><<^^<v^<>><v^<>>vv^>vvv>^.>>>><<v^vvv.<^>v^<>>.>>vv<v>>.<v>.<>v>..v><<<v.v>><>v<.v<<v.^v>>^><^<^<>^>><>v>.><>v>#
#>^^vv>.^.^<^><^>^.v<<<^>v^v<^vv>><vv<<<^v^<v>vvvv^v>^<..<<<..>v^.>.>^><v>^<^<^v>>^<v..<^vv..>^^vv>>v.<.^^^><>>><><^>^v.>#
#>^^>^<>^><><<v^<^^>^v<<^vv.v<^<.<<^v<<v>^^v.v>.^>v>.<><^v<^<^<vv^^v><.>v<<^<<>>vvv^v^.>>v.v>v.^v<>.<<<<<v.vv^>^<^v^^^^<.#
#><v<<<>^v<<^^v>^^v^.^<vvvv<>>><>>^<v>^v<.<>>^..<.v^v><.^<vv>v>v<^>v^.^.<>><<v.^^v>v^>..v.<v<><<^<^>>><^.^<>^>v><><>v.<^<#
#><>.<^<^^v^v><..<^>v><v>v^><.^><v>^^v^^vv^^>^<v^v><<^vv<^><><vv><vv^^^v^v^>v><>v^>><>v^<v^^>v>.>>>^>.v><>v<<<>>^<<<>vv^<#
#><<<<>^^<.^.<v^vvv<><>v<^<v<>v^^v^v.<<v.><<v^<v<<<><<v^v>v<v>>.>v<^>>><<<^v^>>v<<^vv<.^^>v>v.<^>^<^>^>v<>>>v^vv.>.v^>v..#
#>><v>.^^<>.^v^<^v^v^><^^>^>vvv<>>^><...>v^.>^.>^v>>>^>>.<<^^><^.^.v.^v<.<>^v<<>v<.v>^v>>><<.<v^<^<<.<v>v>>^^v.^v^v<<^<..#
#>>^v><<^>v^.><v^><>vv^^^^^.>^vv.^<<vv>><^^vv>>^^>.><^v>v^<>^.>v>...v<^v^>.v^<>^.>vv>vv>^><><>^<>><>v^^v^<v>>^>^<v<>^>v^<#
#.^>^.><>^<^^<v<^^v^v<<<^<<>v..^<^<.v<v<^.<<<<<>>.^.^><^.><v<.v<<v<^>^v>>v>v.^>^><v>v.>vv..<^>>>v^v<vv><^<<>><<<<>>.<<>v<#
#><>>vvv><>>^^<.<<<>>^><>>vv>.<<<>v>v<v.<vv><>.>>^>^<<v^v^>v<>>^v<v>>^.^<^^.>v>^^v^>.<^.v.<vv^^v<^v^>>^^v><^<v<>^<.<v>^<>#
#>>vv<v^vv^^^<vvv^<.v^.^>^<<^v^<v<^<>>>.vvv^<<v>v>><<^v.^vvv<.<^vv.v<^v^v<>>v.^>>v.<<>>><^.v<v.v<<^>v.<<^>^^^>^.>>^^v.<^>#
#<><>><^v^><^<.>^^v<><vv<<>v>^<vv.<^v>v^^^.<><v.v<><^^^^^^v^><>v^>>.v^<^<<v<><.>>.v^vv<.>.^^v^vvv^<^<<^^>v^v>v>.^<v>v<v<<#
#<v<^><><.>^<<^<<v><v>^<v.^<<<^v><^v>>>.vv^v<.>v><vv>>>.<^.<<^^<><>>..^^<>^v>v^v<vv.v^>>^v^>..>^<^vv.>>^<<.v>>^>>>^.v<<>>#
########################################################################################################################.#
""".trimIndent()