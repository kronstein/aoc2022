fun main() {
    val herd = input21.split("\n").mapNotNull { line ->
        "(....): (\\d+)".toRegex().find(line)?.let {
            val (monkey, number) = it.destructured
            Monkey21.Number(monkey, number.toLong())
        } ?:
        "(....): (....) (.) (....)".toRegex().find(line)?.let {
            val (monkey, monkey1, op, monkey2) = it.destructured
            when (op) {
                "+" -> Monkey21.Plus(monkey, monkey1, monkey2)
                "-" -> Monkey21.Minus(monkey, monkey1, monkey2)
                "*" -> Monkey21.Mult(monkey, monkey1, monkey2)
                "/" -> Monkey21.Div(monkey, monkey1, monkey2)
                else -> error("Unknown operation: $op")
            }
        }
    }.associateBy { it.name }.toMutableMap()

    val root = herd["root"]!! as Monkey21.Dependent
    val humn = herd["humn"]!!

    val left = herd[root.name1]!!
    val right = herd[root.name2]!!

    var result = 0L

    if (left.dependsOn(humn.name, herd)) {
        val rightResult = right.execute(herd)
        result = left.expect(rightResult, humn.name, herd).second
    }

    if (right.dependsOn(humn.name, herd)) {
        val leftResult = left.execute(herd)
        result = right.expect(leftResult, humn.name, herd).second
    }

    // Test
    herd["humn"] = Monkey21.Number("humn", result)
    if (left.execute(herd) != right.execute(herd)) {
        error("WTF")
    } else {
        println(herd["humn"]!!.execute(herd))
    }
}
