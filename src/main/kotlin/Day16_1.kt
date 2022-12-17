import kotlin.math.min

data class Valve(
    val name: String,
    val rate: Int,
    val tunnel: List<String>,
)

data class Path(
    val flowSoFar: Int = 0,
    val history: List<MinuteInfo> = listOf(),
) {
    override fun toString(): String {
        return history.joinToString("\n") { it.toString() } + "\nResult: $flowSoFar"
    }
}

sealed class MinuteInfo(
    open val minute: Int,
    open val valvesOpen: Set<Valve>,
) {
    data class Stay(
        override val minute: Int,
        override val valvesOpen: Set<Valve>,
        val valve: Valve,
    ): MinuteInfo(minute, valvesOpen) {
        override fun toString(): String {
            return super.toString() + "\nYou stay at ${valve.name}"
        }
    }
    data class Open(
        override val minute: Int,
        override val valvesOpen: Set<Valve>,
        val valve: Valve,
    ): MinuteInfo(minute, valvesOpen) {
        override fun toString(): String {
            return super.toString() + "\nYou open ${valve.name}"
        }
    }
    data class Move(
        override val minute: Int,
        override val valvesOpen: Set<Valve>,
        val valveFrom: Valve,
        val valveTo: Valve,
        val stepNumber: Int,
        val stepTotal: Int,
    ): MinuteInfo(minute, valvesOpen) {
        override fun toString(): String {
            return super.toString() + "\nYou move from ${valveFrom.name} to ${valveTo.name} in $stepNumber of $stepTotal step."
        }
    }

    override fun toString(): String {
        return "\n== Minute $minute ==\n" + when {
            valvesOpen.isEmpty() -> "No valves are open."
            valvesOpen.size == 1 -> "Valve ${valvesOpen.sortedBy { it.name }.joinToString(", ") { it.name }} is open. Releasing ${valvesOpen.sumOf { it.rate }} pressure."
            else -> "Valves ${valvesOpen.sortedBy { it.name }.joinToString(", ") { it.name }} are open. Releasing ${valvesOpen.sumOf { it.rate }} pressure."
        }
    }
}

fun main() {
    val valves = input16.split("\n").map {
        val (name, rate, connections) = "Valve (.+) has flow rate=(\\d+); tunnel.? lead.? to valve.? (.+)".toRegex().find(it)!!.destructured
        name to Valve(name, rate.toInt(), connections.split(", "))
    }.toMap()

    val current = valves["AA"]!!
    println(turn(
        minutes = 1,
        current = current,
        all = valves,
        open = setOf(),
        path = Path(),
    ))
}

fun turn(
    minutes: Int,
    current: Valve,
    all: Map<String, Valve>,
    open: Set<Valve>,
    path: Path,
): Path {
    val sorted = all.values.filter { !open.contains(it) && current != it && it.rate > 0 }

    if (sorted.isEmpty() || minutes == 30) {
        return Path(
            flowSoFar = path.flowSoFar + (30 - minutes) * open.sumOf { it.rate },
            history = path.history + List(31 - minutes) { index -> MinuteInfo.Stay(minutes + index, open, current) },
        )
    }

    return sorted.map { target ->
        target to goWithoutOpeningValves(
            target = target,
            test = current.tunnel.mapNotNull { all[it] },
            all = all,
        )
    }.filter { pair ->
        val (_, time) = pair
        time + minutes < 29
    }.map { pair ->
        val (target, time) = pair
        turn(
                minutes = minutes + time + 1,
                current = target,
                all = all,
                open = open + target,
                path = Path(
                    flowSoFar = path.flowSoFar + (time + 1) * open.sumOf { it.rate } + target.rate,
                    history = path.history +
                            List(time) { index -> MinuteInfo.Move(minutes + index, open, current, target, index + 1, time)} +
                            MinuteInfo.Open(minutes + time, open, target)
                )
        )
    }.maxByOrNull { it.flowSoFar } ?:
        Path(
            flowSoFar = path.flowSoFar + (30 - minutes) * open.sumOf { it.rate },
            history = path.history + List(31 - minutes) { index -> MinuteInfo.Stay(minutes + index, open, current) },
        )
}

/**
 * Go to target valve the shortest way.
 * Returns number of minutes required for this movement.
 */
fun goWithoutOpeningValves(
    target: Valve,
    test: List<Valve>,
    all: Map<String, Valve>,
): Int {
    if (test.contains(target)) {
        // One minute is required.
        return 1
    }

    return 1 + goWithoutOpeningValves(
        target = target,
        test = test.flatMap { valveLeadTo ->
            valveLeadTo.tunnel.mapNotNull { all[it] }
        }.distinct(),
        all = all,
    )
}

val input16 = """
Valve NV has flow rate=5; tunnels lead to valves ZV, CG, YB, HX, OY
Valve NU has flow rate=6; tunnels lead to valves DA, MA, OA, DK
Valve VU has flow rate=0; tunnels lead to valves PS, FX
Valve JW has flow rate=0; tunnels lead to valves AA, MD
Valve RI has flow rate=0; tunnels lead to valves OY, DG
Valve DG has flow rate=9; tunnels lead to valves TG, RI, DF, EV, KW
Valve PH has flow rate=7; tunnels lead to valves KW, OW, LT, LZ
Valve KZ has flow rate=12; tunnels lead to valves ET, QV, CK, MS
Valve IX has flow rate=0; tunnels lead to valves TS, DO
Valve MS has flow rate=0; tunnels lead to valves LZ, KZ
Valve IL has flow rate=0; tunnels lead to valves DO, ET
Valve EJ has flow rate=20; tunnels lead to valves AV, JY
Valve DK has flow rate=0; tunnels lead to valves NU, CG
Valve YB has flow rate=0; tunnels lead to valves NV, PS
Valve OA has flow rate=0; tunnels lead to valves YA, NU
Valve DA has flow rate=0; tunnels lead to valves NU, RG
Valve KO has flow rate=0; tunnels lead to valves AA, TG
Valve RG has flow rate=4; tunnels lead to valves DF, DA, ZV, MD, LB
Valve MA has flow rate=0; tunnels lead to valves AA, NU
Valve OW has flow rate=0; tunnels lead to valves DO, PH
Valve KW has flow rate=0; tunnels lead to valves DG, PH
Valve DO has flow rate=14; tunnels lead to valves IX, IL, CZ, OW
Valve DF has flow rate=0; tunnels lead to valves RG, DG
Valve TG has flow rate=0; tunnels lead to valves DG, KO
Valve LB has flow rate=0; tunnels lead to valves RG, FX
Valve HX has flow rate=0; tunnels lead to valves AA, NV
Valve GB has flow rate=0; tunnels lead to valves AV, XK
Valve CG has flow rate=0; tunnels lead to valves DK, NV
Valve LT has flow rate=0; tunnels lead to valves AO, PH
Valve FX has flow rate=23; tunnels lead to valves LB, HY, VU
Valve ET has flow rate=0; tunnels lead to valves IL, KZ
Valve CK has flow rate=0; tunnels lead to valves UX, KZ
Valve LZ has flow rate=0; tunnels lead to valves PH, MS
Valve YA has flow rate=17; tunnels lead to valves JY, OA
Valve TS has flow rate=0; tunnels lead to valves NO, IX
Valve NO has flow rate=8; tunnel leads to valve TS
Valve XK has flow rate=24; tunnel leads to valve GB
Valve PS has flow rate=18; tunnels lead to valves EV, VU, YB
Valve AA has flow rate=0; tunnels lead to valves JW, HX, MA, KO
Valve MD has flow rate=0; tunnels lead to valves JW, RG
Valve JM has flow rate=19; tunnels lead to valves QV, HY, AO
Valve AV has flow rate=0; tunnels lead to valves EJ, GB
Valve AO has flow rate=0; tunnels lead to valves JM, LT
Valve JY has flow rate=0; tunnels lead to valves YA, EJ
Valve OY has flow rate=0; tunnels lead to valves NV, RI
Valve UX has flow rate=13; tunnels lead to valves CZ, CK
Valve HY has flow rate=0; tunnels lead to valves JM, FX
Valve EV has flow rate=0; tunnels lead to valves PS, DG
Valve CZ has flow rate=0; tunnels lead to valves UX, DO
Valve ZV has flow rate=0; tunnels lead to valves NV, RG
Valve QV has flow rate=0; tunnels lead to valves JM, KZ
""".trimIndent()