fun main() {
    var sumScore = 0
    val rules = mapOf(
        'X' to Pair(0, mapOf('A' to 'C', 'B' to 'A', 'C' to 'B')),
        'Y' to Pair(3, mapOf('A' to 'A', 'B' to 'B', 'C' to 'C')),
        'Z' to Pair(6, mapOf('A' to 'B', 'B' to 'C', 'C' to 'A')),
    )

    val scoreForLine: (String) -> Int = { line ->
        val my = line.last()
        val their = line.first()
        rules[my]!!.first + (rules[my]!!.second[their]!! + 1 - 'A')
    }

    input2.split('\n').forEach {
        sumScore += scoreForLine(it)
    }

    println(sumScore)
}
