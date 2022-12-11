class Monkey(
    val other: List<Monkey>,
    var items: MutableList<Long> = mutableListOf(),
    var operation: (Long) -> Long = { 0 },
    var div: Long = 0,
    var ifTrue: Int = -1,
    var ifFalse: Int = -1,
) {
    private val number: Int
        get() = other.indexOf(this)

    var count: Int = 0
        private set

    private val commonDenominator by lazy {
        var x = 1L
        other.map { it.div }.forEach {
            x *= it
        }
        x
    }

    operator fun invoke() {
        while(items.size > 0) {
            val old = items.removeFirst().also {
                count++
            }
            val new = operation(old) % commonDenominator
            if (new < 0) {
                error("Overflow on operation: new=$new old=$old")
            }
            if (new % div == 0L) {
                other[ifTrue].items.add(new)
            } else {
                other[ifFalse].items.add(new)
            }
        }
    }

    override fun toString(): String {
        return "Monkey $number inspected items $count times."
    }
}

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
            monkeys.last().operation = { old -> (old + arg.toInt()) / 3 }
        }
        "Operation: new = old \\* (\\d+)".toRegex().find(line)?.let {
            val (arg) = it.destructured
            monkeys.last().operation = { old -> old * arg.toInt() / 3 }
        }
        "Operation: new = old \\* old".toRegex().find(line)?.let {
            monkeys.last().operation = { old -> old * old / 3 }
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

    for(i in 1..20) {
        monkeys.forEach { it() }
    }

    println(monkeys.sortedByDescending { it.count }.take(2).zipWithNext { a, b -> a.count * b.count }.first())
}

val input11 = """
Monkey 0:
  Starting items: 75, 75, 98, 97, 79, 97, 64
  Operation: new = old * 13
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 7

Monkey 1:
  Starting items: 50, 99, 80, 84, 65, 95
  Operation: new = old + 2
  Test: divisible by 3
    If true: throw to monkey 4
    If false: throw to monkey 5

Monkey 2:
  Starting items: 96, 74, 68, 96, 56, 71, 75, 53
  Operation: new = old + 1
  Test: divisible by 11
    If true: throw to monkey 7
    If false: throw to monkey 3

Monkey 3:
  Starting items: 83, 96, 86, 58, 92
  Operation: new = old + 8
  Test: divisible by 17
    If true: throw to monkey 6
    If false: throw to monkey 1

Monkey 4:
  Starting items: 99
  Operation: new = old * old
  Test: divisible by 5
    If true: throw to monkey 0
    If false: throw to monkey 5

Monkey 5:
  Starting items: 60, 54, 83
  Operation: new = old + 4
  Test: divisible by 2
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 6:
  Starting items: 77, 67
  Operation: new = old * 17
  Test: divisible by 13
    If true: throw to monkey 4
    If false: throw to monkey 1

Monkey 7:
  Starting items: 95, 65, 58, 76
  Operation: new = old + 5
  Test: divisible by 7
    If true: throw to monkey 3
    If false: throw to monkey 6
""".trimIndent()
