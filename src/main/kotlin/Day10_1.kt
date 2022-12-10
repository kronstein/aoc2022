fun main() {
    var x = 1
    val check = listOf(20, 60, 100, 140, 180, 220)

    println(input10.split("\n").flatMap {
        when(it) {
            "noop" -> listOf("noop")
            else -> listOf("noop", it)
        }
    }.mapIndexedNotNull { index, s ->
        val cycle = index + 1
        if (cycle in check) {
            cycle * x
        } else {
            null
        }.also {
            "addx (-?\\d+)".toRegex().find(s)?.let {
                val (d) = it.destructured
                x += d.toInt()
            }
        }
    }.sum())
}

val input10 = """
addx 1
noop
addx 4
noop
noop
noop
addx 6
addx -1
addx 5
noop
noop
noop
addx 5
addx -14
noop
addx 19
noop
addx 1
addx 4
addx 1
noop
noop
addx 2
addx 5
addx -27
addx 20
addx -30
addx 2
addx 5
addx 2
addx 4
addx -3
addx 2
addx 5
addx 2
addx -9
addx 1
addx 11
noop
addx -20
addx 7
addx 23
addx 2
addx 3
addx -2
addx -34
addx -2
noop
addx 3
noop
addx 3
addx 2
noop
addx 3
addx 2
addx 5
addx 2
addx -9
addx -7
addx 21
noop
addx 8
noop
addx -1
addx 3
addx -2
addx 5
addx -37
noop
addx 35
addx -31
addx 1
addx 4
addx -1
addx 2
noop
addx 3
addx 1
addx 5
addx -2
addx 7
addx -2
addx -2
addx 10
noop
addx 4
noop
noop
addx -19
addx 20
addx -38
noop
noop
addx 7
addx 2
addx 3
noop
addx 4
addx -3
addx 2
addx 2
noop
addx 3
noop
noop
noop
addx 5
noop
addx 7
addx -2
addx 7
noop
noop
addx -5
addx 6
addx -36
noop
addx 1
addx 2
addx 5
addx 2
addx 3
addx -2
addx 2
addx 5
addx 2
addx 1
noop
addx 4
addx -16
addx 21
noop
noop
addx 1
addx -8
addx 12
noop
noop
noop
noop
""".trimIndent()