import java.util.*

fun main() {
    var elves = input23.split("\n").mapIndexed { y, s ->
        s.mapIndexedNotNull { x, c ->
            when(c) {
                '#' -> Elf(y = y, x = x)
                else -> null
            }
        }
    }.flatten()

    val check = mutableListOf(
        Proposition(constraints = listOf(Constraint(-1, 0), Constraint(-1, 1), Constraint(-1, -1)), y = -1, x = 0), // N, NE, NW
        Proposition(constraints = listOf(Constraint(1, 0), Constraint(1, 1), Constraint(1, -1)), y = 1, x = 0), // S, SE, SW
        Proposition(constraints = listOf(Constraint(0, -1), Constraint(-1, -1), Constraint(1, -1)), y = 0, x = -1), // W, NW, SW
        Proposition(constraints = listOf(Constraint(0, 1), Constraint(-1, 1), Constraint(1, 1)), y = 0, x = 1), // E, NE, SE
    )

    println("\n== Initial state ==")
    elves.print()

    var round = 1
    while(true) {
        val propositions = elves.mapNotNull { elf ->
            val adjacent = findAdjacent(elf, elves)
            if (adjacent.isEmpty()) {
                // This elf will not move.
                null
            } else {
                check.firstOrNull { proposition ->
                    proposition.isValid(elf, adjacent)
                }?.let {
                    elf to it
                }
            }
        }

        val targets = propositions.groupBy { (elf, proposition) ->
            Final(elf.x + proposition.x, elf.y + proposition.y)
        }

        val moved = targets.flatMap {
            listOfNotNull(it.value.singleOrNull())
        }

        elves = elves.filter { it !in moved.map { (elf, _) -> elf } } + moved.map { (elf, proposition) -> elf.copy(
            y = elf.y + proposition.y,
            x = elf.x + proposition.x,
        )}

        Collections.rotate(check, -1)

        println("\n== After round #$round ==")
        //elves.print()

        if (moved.isEmpty()) {
            println("===>>> No moves after round #$round <<<===")
            return
        }

        round++
    }
}