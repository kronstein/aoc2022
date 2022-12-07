fun main() {
    val root = File7.build(input7)

    val freeSpace = 70000000 - root.size()
    val minSize = 30000000 - freeSpace

    println(root.flatMap().plus(root).filter {
        it.child.size > 0 && it.size() >= minSize
    }.minByOrNull { it.size() }?.size())
}
