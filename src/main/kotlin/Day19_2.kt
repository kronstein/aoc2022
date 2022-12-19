fun main() {
    println(input19.split("\n").map { line ->
        val (oreRobotCostsOre, clayRobotCostsOre, obsidianRobotCostsOre, obsidianRobotCostsClay, geodeCrackerCostsOre, geodeCrackerCostsObsidian) = "Blueprint \\d+: Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.".toRegex().find(line)!!.destructured
        Blueprint(
            oreCollectorRequiresOre = oreRobotCostsOre.toInt(),
            clayCollectorRequiresOre = clayRobotCostsOre.toInt(),
            obsidianCollectorRequiresOre = obsidianRobotCostsOre.toInt(),
            obsidianCollectorRequiresClay = obsidianRobotCostsClay.toInt(),
            geodeCrackerRequiresOre = geodeCrackerCostsOre.toInt(),
            geodeCrackerRequiresObsidian = geodeCrackerCostsObsidian.toInt(),
        )
    }.take(3).map { blueprint ->
        getLargestNumberOfGeodes(blueprint)
    }.reduce { acc, i -> acc * i })
}

fun getLargestNumberOfGeodes(blueprint: Blueprint): Int {
    var states = setOf(State())
    for(i in 1 until 32) {
        println("Minute $i -- possibilities (${states.size})")
        states = minute2(blueprint, states)
    }
    return states.maxOf { it.openGeode }
}

fun minute2(
    blueprint: Blueprint,
    states: Set<State>,
): Set<State> {
    val maxGeodeCrackers = states.maxByOrNull { it.geodeCrackers }?.geodeCrackers ?: 0
    val withGeodeCrackers = states.filter { it.geodeCrackers > 0 && it.geodeCrackers == maxGeodeCrackers }
    return if (withGeodeCrackers.isEmpty()) {
        states.flatMap { state ->
            state.getNextStates(blueprint)
        }.toSet()
    } else {
        withGeodeCrackers.flatMap { state ->
            state.getNextStates(blueprint)
        }.toSet()
    }
}