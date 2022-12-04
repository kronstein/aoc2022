fun main() {
    val count = input4.split("\n").map {
        it.split(',').map { range ->
            val (start, end) = "(\\d+)-(\\d+)".toRegex().find(range)!!.destructured
            IntRange(start.toInt(), end.toInt())
        }.zipWithNext().first()
    }.count { pair ->
        pair.first.any { pair.second.contains(it) } ||
                pair.second.any { pair.first.contains(it) }
    }

    println(count)
}
