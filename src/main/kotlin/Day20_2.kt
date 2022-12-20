fun main() {
    val sequence = input20.split("\n").map { Number(it.toInt(), 811589153) }

    var result = sequence
    for(i in 1..10) {
        sequence.forEach {
            result = mix(result, it)
        }
    }

    val zeroAt = result.indexOfFirst { it.value == 0L }
    println(result[(1000 + zeroAt) % sequence.size].value +
            result[(2000 + zeroAt) % sequence.size].value +
            result[(3000 + zeroAt) % sequence.size].value)
}