fun main() {
    val cubes = input18.split("\n").map { line ->
        val cube = line.split(",")
        Cube(
            pos = cube.map { it.toInt() }
        )
    }.map {
        XYZ(it.pos[0], it.pos[1], it.pos[2])
    }

    val maxX = cubes.maxOf { it.x } + 1
    val minX = cubes.minOf { it.x } - 1
    val maxY = cubes.maxOf { it.y } + 1
    val minY = cubes.minOf { it.y } - 1
    val maxZ = cubes.maxOf { it.z } + 1
    val minZ = cubes.minOf { it.z } - 1

    println("x=$minX .. $maxX  y=$minY .. $maxY  z=$minZ .. $maxZ")

    // Scan sides for Y from top to down and down to top
    val bound = bound(
        cubes.toSet(),
        setOf(XYZ(minX, minY, minZ)),
        minX..maxX,
        minY..maxY,
        minZ..maxZ,
    ).toList()

    var sides = 0
    for(i in cubes.indices) {
        for(j in bound.indices) {
            if (cubes[i].isAdjacent(bound[j])) {
                sides++
            }
        }
    }
    println(sides)
}

fun bound(
    obsidian: Set<XYZ>,
    bound: Set<XYZ>,
    xRange: IntRange,
    yRange: IntRange,
    zRange: IntRange,
): Set<XYZ> {
    val test = bound.flatMap { b ->
        listOf(
            b.copy(x = b.x - 1), b.copy(x = b.x + 1),
            b.copy(y = b.y - 1), b.copy(y = b.y + 1),
            b.copy(z = b.z - 1), b.copy(z = b.z + 1)
        ).filter {
            it.x in xRange && it.y in yRange && it.z in zRange && !obsidian.contains(it) && !bound.contains(it)
        }
    }

    if (test.isEmpty()) return bound
    return bound(
        obsidian,
        bound + test,
        xRange, yRange, zRange,
    )
}
