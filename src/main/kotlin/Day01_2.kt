fun main() {
    val elves = mutableListOf(0L)

    input1.split('\n').forEach {
        when(val calory = it.toIntOrNull()) {
            null -> elves.add(0L)
            else -> elves[elves.size - 1] = elves[elves.size - 1] + calory
        }
    }

    println(elves.sortedDescending().take(3).sum())
}
