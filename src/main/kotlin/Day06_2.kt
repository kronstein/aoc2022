fun main() {
    println(input6.mapIndexedNotNull { index, _ ->
        val end = (index + 14).coerceAtMost(input6.length)
        val s = input6.substring(index, end)
        if (s.toList().toSet().size == 14) index + 14 else null
    }.first())
}
