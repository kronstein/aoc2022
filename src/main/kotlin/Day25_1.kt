import kotlin.math.max

fun main() {
    val list = input25.split("\n")
    var sum = "0"
    list.forEach {
        sum = sum.snafuPlus(it)
    }
    println(sum)
}

fun String.snafuPlus(s: String): String {
    val length = max(s.length, this.length)
    val left = "0".repeat(length - this.length) + this
    val right = "0".repeat(length - s.length) + s
    var current = ""
    var remember = 0
    for(i in length - 1 downTo 0) {
        val c1 = left[i].toSnafuInt()
        val c2 = right[i].toSnafuInt()
        when(val d = c1 + c2 + remember) {
            0 -> current = "0$current".also { remember = 0 }
            1 -> current = "1$current".also { remember = 0 }
            2 -> current = "2$current".also { remember = 0 }
            -1 -> current = "-$current".also { remember = 0 }
            -2 -> current = "=$current".also { remember = 0 }

            3 -> current = "=$current".also { remember = 1 }
            4 -> current = "-$current".also { remember = 1 }
            5 -> current = "0$current".also { remember = 1 }
            -3 -> current = "2$current".also { remember = -1 }
            -4 -> current = "1$current".also { remember = -1 }
            -5 -> current = "0$current".also { remember = -1 }
            else -> error("Not supported $d")
        }
    }
    return when(remember) {
        0 -> current
        1 -> "1$current"
        else -> error("Not possible")
    }
}

fun Char.toSnafuInt(): Int {
    return when(this) {
        '0' -> 0
        '1' -> 1
        '2' -> 2
        '-' -> -1
        '=' -> -2
        else -> error("Unknown char $this")
    }
}

val input25 ="""
2-=0102020-02
1-02202=-1=-2==
1==00-101-=0-
211012=12222
112-20
11==0020-=20-=0=12=
122-22110
1==00-1201112==--=
1-10==11=
10=0=110-2
1111=000=2001=211=
22-21===0=-
2=022
11=-0100===
10=10--==-0
12=01-1
12-01--20121-2-=0-1
22-222--022-0-=01-
12-1
2-=2=02-1=
2210
1=2=0211
10=01=001-01
2
201=-1
11---10
1=-0=
1=-021
1-2=02012111=01=-100
1101=20=
20-1-
2-1020---
2=121=0=10=
1022-22211112=-1
2-1-
210--==00
12=1122-10
1=2222=2-=21=0
1=021=
1=10-1201
1=1
1-=-==0201-=1=0
22=2=211-0-1=2=1
1=21-=002111
102112=10021
1===-111-=2001122
10--
1-
1-2-2
11-1-1-02-
1=20--=1=0101-1001
12012-
1220-1--00
1=2=1-
12-0=2022=0100-1
12=2-=-=221==2=
2-2-
1=-1-01-0=
12---2=
1=1=1102-==010
10-=--200=
1=2212=-
12-121=-
1===0-=20-=
1=2020-01---112=1-
101=00111=20=
1=2=01=1-2==0-
2=-100---00
1==1=01--===0-10-
1==-=0-20
1=2011121=10--2
2-=0-01-=--12102=
200
2==22=0002-210
102
1-22221
20==00
1-22--2-0=10=11-1
1=12=2
1=-12-=
10-020-100=0=
1-11111
1=02-002220
20==0120210--1
100-121=2-2-=00=
21
11=-=-
2-=1100=10102-
112--0-2=1--=2=
1-1==100
1--02-0=1-
1102-
1-2-21-
22=1=-000=210-12
1=112-12-
1====120==0
20=2=22=22=2-=2=
1=21201=
2202202-20
1=-2=-==--200101
12010
1--22=2--02
102==-
2-==1-10
12-==12-2
22=-11110=2-211
1-0
1=02=20-1=--
1-20
1102---10
11=2-==2=1-
2=2-
2=10=2==221=01
1--==-1==
1011-111
1-2-=---012-21
1=-==0
1=0011=11112102=
10-0-=202
2-----12=
1=-2221020=01-10
12-2201==
1-102
22-100=0-===
100=0-0
""".trimIndent()