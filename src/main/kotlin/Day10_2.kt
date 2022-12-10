fun main() {
    var x = 1
    var crt = ""

    input10.split("\n").flatMap {
        when(it) {
            "noop" -> listOf("noop")
            else -> listOf("noop ${it.substring(5)}", it)
        }
    }.forEachIndexed { index, s ->
        val cycle = index + 1

        when (cycle % 40) {
            in x .. x + 2 -> crt += '#'
            else -> crt += '.'
        }

        if (cycle % 40 == 0) {
            crt += '\n'
        }

        "addx (-?\\d+)".toRegex().find(s)?.let {
            val (d) = it.destructured
            x += d.toInt()
        }
    }

    println(crt)
}