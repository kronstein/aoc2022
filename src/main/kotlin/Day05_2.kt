fun main() {
    val stacks = mutableListOf<ArrayDeque<String>>()

    input5.split("\n").takeWhile { it.isNotEmpty() }.forEach {
        val count = (it.length + 1) / 4
        while(stacks.size < count) {
            stacks.add(ArrayDeque())
        }
        for(i in it.indices step 4) {
            val cargo = it.substring(i + 1, i + 2).trim()
            if (cargo.isNotEmpty()) {
                stacks[i / 4].add(cargo)
            }
        }
    }

    input5.split("\n").mapNotNull { "move (\\d+) from (\\d+) to (\\d+)".toRegex().find(it) }.forEach {
        val (count, from, to) = it.destructured
        val temp = ArrayDeque<String>()
        for(i in 1..count.toInt()) {
            temp.addFirst(stacks[from.toInt() - 1].removeFirst())
        }
        for(i in 1..count.toInt()) {
            stacks[to.toInt() - 1].addFirst(temp.removeFirst())
        }
    }

    println(stacks.joinToString(separator = "") { it.first() })
}
