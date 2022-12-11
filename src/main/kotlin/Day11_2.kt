import java.math.BigInteger

fun main() {
    val monkeys = mutableListOf<Monkey>()
    input11.split("\n").map { it.trim() }.forEach { line ->
        "Monkey (\\d+):".toRegex().find(line)?.let {
            monkeys.add(Monkey(other = monkeys))
        }
        "Starting items: (.+)".toRegex().find(line)?.let {
            val (items) = it.destructured
            monkeys.last().items = items.split(", ").map { item -> item.toLong() }.toMutableList()
        }
        "Operation: new = old \\+ (\\d+)".toRegex().find(line)?.let {
            val (arg) = it.destructured
            monkeys.last().operation = { old -> old + arg.toInt() }
        }
        "Operation: new = old \\* (\\d+)".toRegex().find(line)?.let {
            val (arg) = it.destructured
            monkeys.last().operation = { old -> old * arg.toInt() }
        }
        "Operation: new = old \\* old".toRegex().find(line)?.let {
            monkeys.last().operation = { old -> old * old }
        }
        "Test: divisible by (\\d+)".toRegex().find(line)?.let {
            val (div) = it.destructured
            monkeys.last().div = div.toLong()
        }
        "If true: throw to monkey (\\d+)".toRegex().find(line)?.let {
            val (monkey) = it.destructured
            monkeys.last().ifTrue = monkey.toInt()
        }
        "If false: throw to monkey (\\d+)".toRegex().find(line)?.let {
            val (monkey) = it.destructured
            monkeys.last().ifFalse = monkey.toInt()
        }
    }

    for(i in 1..10000) {
        monkeys.forEach { it() }
        when(i) {
            1, 20, 1000 -> {
                println("\n=== After round $i ===")
                println(monkeys.joinToString("\n"))
            }
        }
    }

    println(monkeys.sortedByDescending { it.count }.take(2).zipWithNext { a, b -> a.count.toLong() * b.count.toLong() }.first())
}
