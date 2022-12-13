fun main() {
    val test1 = parseLine("[[2]]")
    val test2 = parseLine("[[6]]")
    val list = (input13.split("\n").mapNotNull { line ->
        parseLine(line)
    } + test1 + test2).sortedBy { it }
    println((list.indexOf(test1) + 1) * (list.indexOf(test2) + 1))
}
