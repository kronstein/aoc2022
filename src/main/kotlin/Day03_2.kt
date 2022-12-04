fun main() {
    var sumPriority = 0

    val lines = input3.split('\n')
    for(i in lines.indices step 3) {
        val first = lines[i].toList()
        val second = lines[i + 1].toSet()
        val third = lines[i + 2].toSet()
        val common = first.intersect(second).intersect(third)
        sumPriority += when(val c = common.first()) {
            in 'a'..'z' -> c - 'a' + 1
            in 'A'..'Z' -> c - 'A' + 27
            else -> error("No solution")
        }
    }

    println(sumPriority)
}
