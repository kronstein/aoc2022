data class SavedState(
    val rockIndex: Int,
    val jetIndex: Int,
    val tower: Set<P>,
)

fun main() {
    val rocks = listOf(
        listOf(P(2, 0), P(3, 0), P(4, 0), P(5, 0)),
        listOf(P(2, 1), P(3, 1), P(4, 1), P(3, 0), P(3, 2)),
        listOf(P(2, 0), P(3, 0), P(4, 0), P(4, 1), P(4, 2)),
        listOf(P(2, 0), P(2, 1), P(2, 2), P(2, 3)),
        listOf(P(2, 0), P(3, 0), P(2, 1), P(3, 1)),
    )

    var tower = setOf(
        P(0, 0), P(1, 0), P(2, 0), P(3, 0), P(4, 0), P(5, 0), P(6, 0)
    )

    var repeat = mutableMapOf<SavedState, Pair<Long, Long>>()

    var rockIndex = 0
    var jetIndex = 0
    var savedHeight = 0L
    var i = 1L
    while(i <= 1000000000000L) {
        // Create new rock 3 units above tower.
        val spawnY = 4 + tower.maxByOrNull { it.y }!!.y
        var fallingRock: List<P>? = rocks[rockIndex % rocks.size].map { it.copy(y = it.y + spawnY) }
        while(fallingRock != null) {
            val jetPush = when(input17[jetIndex % input17.length]) {
                '>' -> 1
                '<' -> -1
                else -> error("Wrong symbol in input17")
            }
            fallingRock = fallingRock.tryPushX(x = jetPush, tower)
            fallingRock.tryFallY(y = -1, tower).let {
                fallingRock = it.first
                tower = it.second
            }
            jetIndex = (jetIndex + 1) % input17.length
        }
        rockIndex = (rockIndex + 1) % rocks.size
        i++

        val optimized = tower.optimized()
        if (optimized > 0) {
            tower = tower.filter { it.y >= optimized }.map { it.copy(y = it.y - optimized) }.toSet()
            savedHeight += optimized
            println("Optimized: $savedHeight")
            val savedState = SavedState(rockIndex, jetIndex, tower)
            val previous = repeat.put(savedState, i to savedHeight)
            if (previous != null) {
                println("Repeat found at step $i (the same situation was at step ${previous.first}). Previous saved height was ${previous.second}, this height is $savedHeight")
                val steps = i - previous.first
                val remainingRocks = 1000000000000L - i
                val cycles = remainingRocks / steps
                println("Speed up $cycles cycles")
                i += cycles * steps
                savedHeight += (savedHeight - previous.second) * cycles
                //savedHeight += (remainingRocks / i) * (savedHeight - previous)
                //i = (remainingRocks / i) * i
                repeat.clear()
                repeat[savedState] = previous
                println("The simulation skipped to $i, savedHeight = $savedHeight")
            }
        }
    }

    println(savedHeight + tower.maxByOrNull { it.y }!!.y)
}

fun Set<P>.optimized(): Int {
    return groupBy { it.y }.mapNotNull {
        if (it.value.size == 7) {
            it.key
        } else {
            null
        }
    }.maxOf { it }
}
