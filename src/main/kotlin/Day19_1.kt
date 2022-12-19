data class Robot(
    val produceOre: Int = 0,
    val produceClay: Int = 0,
    val produceObsidian: Int = 0,
    val produceOpenGeode: Int = 0,
)

data class State(
    val oreCollectors: Int = 1,
    val clayCollectors: Int = 0,
    val obsidianCollectors: Int = 0,
    val geodeCrackers: Int = 0,
    val ore: Int = 1,
    val clay: Int = 0,
    val obsidian: Int = 0,
    val openGeode: Int = 0,
)

data class Blueprint(
    val oreCollectorRequiresOre: Int,
    val clayCollectorRequiresOre: Int,
    val obsidianCollectorRequiresOre: Int,
    val obsidianCollectorRequiresClay: Int,
    val geodeCrackerRequiresOre: Int,
    val geodeCrackerRequiresObsidian: Int,
)

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
    }.mapIndexed { index, blueprint ->
        getBlueprintQuality(blueprint, index + 1)
    }.sum())
}

fun getBlueprintQuality(blueprint: Blueprint, id: Int): Int {
    var states = setOf(State())
    for(i in 1 until 24) {
        println("Blueprint $id minute $i -- possibilities (${states.size})")
        states = minute(blueprint, states)
    }
    return id * states.maxOf { it.openGeode }
}

fun minute(
    blueprint: Blueprint,
    states: Set<State>,
): Set<State> {
    val withGeodeCrackers = states.filter { it.geodeCrackers > 0 }
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

fun State.getNextStates(blueprint: Blueprint): List<State> {
    val nextStates = mutableListOf(this.copy(
        ore = ore + oreCollectors,
        clay = clay + clayCollectors,
        obsidian = obsidian + obsidianCollectors,
        openGeode = openGeode + geodeCrackers,
    ))
    with(blueprint) {
        if (obsidian >= geodeCrackerRequiresObsidian && ore >= geodeCrackerRequiresOre) {
            nextStates.add(
                copy(
                    geodeCrackers = geodeCrackers + 1,
                    ore = ore - geodeCrackerRequiresOre + oreCollectors,
                    clay = clay + clayCollectors,
                    obsidian = obsidian - geodeCrackerRequiresObsidian + obsidianCollectors,
                    openGeode = openGeode + geodeCrackers,
                )
            )
        }
        if (ore >= obsidianCollectorRequiresOre && clay >= obsidianCollectorRequiresClay) {
            nextStates.add(
                copy(
                    obsidianCollectors = obsidianCollectors + 1,
                    ore = ore - obsidianCollectorRequiresOre + oreCollectors,
                    clay = clay - obsidianCollectorRequiresClay + clayCollectors,
                    obsidian = obsidian + obsidianCollectors,
                    openGeode = openGeode + geodeCrackers,
                )
            )
        }
        if (ore >= clayCollectorRequiresOre) {
            nextStates.add(
                copy(
                    clayCollectors = clayCollectors + 1,
                    ore = ore - clayCollectorRequiresOre + oreCollectors,
                    clay = clay + clayCollectors,
                    obsidian = obsidian + obsidianCollectors,
                    openGeode = openGeode + geodeCrackers,
                )
            )
        }
        if (ore >= oreCollectorRequiresOre) {
            nextStates.add(
                copy(
                    oreCollectors = oreCollectors + 1,
                    ore = ore - oreCollectorRequiresOre + oreCollectors,
                    clay = clay + clayCollectors,
                    obsidian = obsidian + obsidianCollectors,
                    openGeode = openGeode + geodeCrackers,
                )
            )
        }
    }
    return nextStates
}

val input19 = """
Blueprint 1: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 15 clay. Each geode robot costs 2 ore and 8 obsidian.
Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 17 clay. Each geode robot costs 3 ore and 10 obsidian.
Blueprint 3: Each ore robot costs 2 ore. Each clay robot costs 2 ore. Each obsidian robot costs 2 ore and 20 clay. Each geode robot costs 2 ore and 14 obsidian.
Blueprint 4: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 4 ore and 15 obsidian.
Blueprint 5: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 13 clay. Each geode robot costs 3 ore and 15 obsidian.
Blueprint 6: Each ore robot costs 2 ore. Each clay robot costs 2 ore. Each obsidian robot costs 2 ore and 15 clay. Each geode robot costs 2 ore and 7 obsidian.
Blueprint 7: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 9 clay. Each geode robot costs 3 ore and 7 obsidian.
Blueprint 8: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 2 ore and 16 clay. Each geode robot costs 2 ore and 8 obsidian.
Blueprint 9: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 20 clay. Each geode robot costs 4 ore and 18 obsidian.
Blueprint 10: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 11 clay. Each geode robot costs 2 ore and 19 obsidian.
Blueprint 11: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 7 clay. Each geode robot costs 3 ore and 10 obsidian.
Blueprint 12: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 11 clay. Each geode robot costs 2 ore and 16 obsidian.
Blueprint 13: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 16 clay. Each geode robot costs 3 ore and 15 obsidian.
Blueprint 14: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 18 clay. Each geode robot costs 3 ore and 13 obsidian.
Blueprint 15: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 13 clay. Each geode robot costs 2 ore and 20 obsidian.
Blueprint 16: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 14 clay. Each geode robot costs 4 ore and 10 obsidian.
Blueprint 17: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 17 clay. Each geode robot costs 3 ore and 16 obsidian.
Blueprint 18: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 20 clay. Each geode robot costs 2 ore and 17 obsidian.
Blueprint 19: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 16 clay. Each geode robot costs 4 ore and 12 obsidian.
Blueprint 20: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 16 clay. Each geode robot costs 3 ore and 20 obsidian.
Blueprint 21: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 18 clay. Each geode robot costs 4 ore and 12 obsidian.
Blueprint 22: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 13 clay. Each geode robot costs 3 ore and 19 obsidian.
Blueprint 23: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 18 clay. Each geode robot costs 3 ore and 8 obsidian.
Blueprint 24: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 13 clay. Each geode robot costs 2 ore and 9 obsidian.
Blueprint 25: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 5 clay. Each geode robot costs 3 ore and 15 obsidian.
Blueprint 26: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 15 clay. Each geode robot costs 3 ore and 16 obsidian.
Blueprint 27: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 20 clay. Each geode robot costs 4 ore and 16 obsidian.
Blueprint 28: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 8 clay. Each geode robot costs 2 ore and 8 obsidian.
Blueprint 29: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 14 clay. Each geode robot costs 4 ore and 19 obsidian.
Blueprint 30: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 10 clay. Each geode robot costs 2 ore and 7 obsidian.
""".trimIndent()