import kotlin.concurrent.thread

fun main() {
    val valves = input16.split("\n").associate {
        val (name, rate, connections) = "Valve (.+) has flow rate=(\\d+); tunnel.? lead.? to valve.? (.+)".toRegex()
            .find(it)!!.destructured
        name to Valve(name, rate.toInt(), connections.split(", "))
    }

    val current = valves["AA"]!!
    println(turn2(
        minutes = 1,
        current = listOf(current, current),
        all = valves,
        open = setOf(),
        openNext = setOf(),
        flowSoFar = 0,
        move = listOf(MoveSpec.Active(), MoveSpec.Active()),
        lastMinute = 26,
    ))
}

sealed class MoveSpec {
    object Passive: MoveSpec()
    data class Active(
        val moveSteps: Int = 0,
        val valve: Valve? = null,
    ): MoveSpec()
}

fun turn2(
    minutes: Int,
    current: List<Valve>,
    all: Map<String, Valve>,
    open: Set<Valve>,
    openNext: Set<Valve>,
    flowSoFar: Int,
    move: List<MoveSpec>,
    lastMinute: Int,
): Int {
    if (flowSoFar == -1) {
        // Parasite solution.
        return flowSoFar
    }

    if (minutes == 1 && current.zipWithNext().any { it.first.name < it.second.name }) {
        // Optimize repeated routes.
        return -1
    }

    if (minutes > lastMinute) {
        return flowSoFar
    }

    if (move.all { it is MoveSpec.Passive} && openNext.isEmpty()) {
        // Fast forward
        return flowSoFar + open.sumOf { it.rate } * (lastMinute - minutes + 1)
    }

    if (move.all { (it is MoveSpec.Active && it.moveSteps > 0) || it is MoveSpec.Passive }) {
        // All moving, increase time.
        return turn2(
            minutes = minutes + 1,
            current = current,
            all = all,
            open = open + openNext,
            openNext = setOf(),
            flowSoFar = flowSoFar + open.sumOf { it.rate },
            move = move.map {
                when (it) {
                    is MoveSpec.Active -> it.copy(moveSteps = it.moveSteps - 1)
                    else -> it
                }
            },
            lastMinute = lastMinute,
        )
    }

    val valvesToOpen = move.filter {
        it is MoveSpec.Active && it.moveSteps == 0 && it.valve != null && !open.contains(it.valve)
    }

    if (valvesToOpen.isNotEmpty()) { // We have reached our destination valve.
        // Open all valves and increase time.
        return turn2(
            minutes = minutes,
            current = current,
            all = all,
            open = open,
            openNext = openNext + valvesToOpen.mapNotNull { (it as MoveSpec.Active).valve },
            flowSoFar = flowSoFar,
            move = move.map {
                when {
                    it is MoveSpec.Active && it.moveSteps == 0 && it.valve != null && !open.contains(it.valve) -> it.copy(
                        moveSteps = 1,
                        valve = null,
                    )
                    else -> it
                }
            },
            lastMinute = lastMinute,
        )
    }

    // We are moving only this index.
    val index = move.indexOf(move.first { it is MoveSpec.Active && it.moveSteps == 0 })

    val sorted = (all.values - open - current - openNext).filter { it.rate > 0 }.sortedByDescending { it.rate }

    return sorted.map { target ->
        target to goWithoutOpeningValves(
            target = target,
            test = current[index].tunnel.mapNotNull { all[it] },
            all = all,
        )
    }.filter { pair ->
        val (_, time) = pair
        time + minutes < lastMinute - 1
    }.map { pair ->
        val (target, time) = pair
        val newMove = move.toMutableList()
        newMove[index] = MoveSpec.Active(moveSteps = time, valve = target)
        val newCurrent = current.toMutableList()
        newCurrent[index] = target
        turn2(
            minutes = minutes,
            current = newCurrent,
            all = all,
            open = open,
            openNext = openNext,
            flowSoFar = flowSoFar,
            move = newMove,
            lastMinute = lastMinute,
        )
    }.maxByOrNull { it }.also {
        when(minutes) {
            1 -> println(". $it")
        }
    } ?: turn2(
        minutes = minutes,
        current = current,
        all = all,
        open = open,
        openNext = openNext,
        flowSoFar = flowSoFar,
        move = move.mapIndexed { i, m ->
            if (i == index) MoveSpec.Passive else m
        },
        lastMinute = lastMinute,
    )
}
