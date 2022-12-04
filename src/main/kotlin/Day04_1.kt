fun main() {
    val count = input4.split("\n").map {
        it.split(',').map { range ->
            val (start, end) = "(\\d+)-(\\d+)".toRegex().find(range)!!.destructured
            IntRange(start.toInt(), end.toInt())
        }.zipWithNext().first()
    }.count { pair ->
        pair.first.all { pair.second.contains(it) } ||
                pair.second.all { pair.first.contains(it) }
    }

    println(count)
}

val input4 = """
8-18,10-19
12-69,8-15
62-77,36-50
26-27,26-91
16-23,24-63
17-43,18-44
29-68,29-70
15-90,28-91
8-39,10-40
47-64,27-63
8-77,78-95
3-65,66-71
20-22,21-98
52-53,53-98
29-30,29-44
86-90,38-87
33-99,31-31
62-80,82-90
45-55,33-54
57-62,58-63
8-59,8-98
7-23,1-22
12-36,22-37
1-31,32-80
94-96,46-95
5-90,4-40
5-5,6-52
74-86,7-87
1-4,6-38
49-65,49-64
21-21,22-99
48-61,11-88
46-51,46-50
13-87,88-88
5-6,6-99
9-40,52-96
4-93,5-92
78-88,77-86
39-72,71-73
31-90,19-91
52-53,53-94
2-4,3-62
92-95,46-91
41-42,41-94
96-98,8-97
9-75,9-74
75-85,67-76
27-32,29-32
69-93,70-94
17-80,18-80
68-68,12-69
36-80,35-60
41-86,41-42
18-82,83-83
48-97,90-92
1-57,16-58
39-89,39-88
17-17,16-53
98-99,8-97
24-71,9-48
37-38,37-52
9-80,10-80
1-18,19-57
55-67,58-73
82-84,85-85
23-92,22-87
9-97,92-96
11-93,94-98
16-93,17-17
81-97,63-80
67-92,67-92
63-67,62-64
15-66,67-81
13-20,13-13
83-83,4-82
10-54,6-6
13-69,8-11
18-19,18-94
9-46,8-19
31-97,62-96
35-94,95-95
21-56,57-57
3-44,3-39
24-95,79-89
33-59,34-34
23-25,21-26
71-97,38-98
37-76,38-77
77-86,64-87
2-12,1-70
34-96,15-97
16-75,74-76
4-99,4-99
12-89,11-12
6-24,5-7
10-43,11-88
11-44,45-83
24-95,2-96
10-73,2-11
3-82,16-31
20-21,20-30
22-87,86-89
44-44,43-47
56-84,56-57
8-80,9-86
1-50,5-51
9-30,8-9
50-87,27-70
37-96,96-96
9-79,46-81
3-98,3-97
65-83,27-38
51-56,1-56
7-97,8-97
33-91,32-90
38-86,87-87
8-91,4-92
8-89,25-88
88-99,48-98
70-80,24-69
28-55,43-50
46-70,7-47
66-94,67-93
18-43,93-99
29-61,29-49
19-71,18-71
21-36,21-36
61-63,62-81
4-5,5-95
90-93,63-89
1-23,4-22
66-83,50-82
63-92,81-98
29-99,29-99
4-8,8-99
30-47,31-36
3-94,93-99
52-52,51-87
76-76,77-80
2-95,6-96
8-97,9-48
12-79,11-78
25-95,14-94
76-96,3-33
1-96,76-99
3-3,2-90
76-91,75-76
12-83,11-11
3-33,19-48
12-98,12-13
15-94,16-98
1-2,4-90
25-59,26-50
27-85,84-85
22-75,22-22
30-39,30-38
4-95,4-95
24-49,10-48
33-34,34-79
3-62,33-61
4-17,3-5
27-58,59-59
72-98,73-98
28-86,87-95
27-57,28-56
49-98,40-92
30-85,84-92
76-78,75-75
33-84,83-83
56-64,42-63
69-90,27-90
67-70,66-67
52-66,56-79
3-81,2-82
9-92,93-96
42-93,94-94
20-32,33-96
41-42,42-94
7-37,6-17
17-44,18-28
79-83,66-78
2-29,1-86
25-29,20-29
38-77,38-38
59-59,59-99
71-96,11-96
48-96,17-85
71-75,67-75
75-82,76-81
8-58,8-58
45-91,35-46
10-14,15-80
87-88,3-99
1-3,7-57
24-37,24-38
10-74,75-81
5-92,5-5
73-96,96-97
22-84,84-85
91-91,3-90
18-20,19-20
4-4,3-91
29-30,30-65
57-64,22-50
25-88,24-68
30-94,94-96
15-42,16-90
17-96,71-87
36-89,35-37
18-71,70-70
13-36,12-94
47-96,46-48
2-94,55-95
82-82,3-82
3-84,1-48
34-85,34-85
9-95,8-95
1-28,1-29
98-98,52-85
55-56,30-55
20-59,19-89
55-56,50-57
96-96,20-95
44-50,15-67
73-74,56-75
54-98,38-55
54-93,54-86
6-94,7-95
23-24,23-34
52-83,9-80
2-95,1-13
52-90,29-91
66-89,66-66
10-10,11-94
65-73,64-97
8-93,62-92
71-72,72-85
2-58,59-59
46-46,47-61
8-8,8-84
6-75,76-76
47-97,24-48
19-19,20-71
13-94,4-87
47-78,48-78
20-74,21-35
11-46,11-46
11-78,77-79
13-74,14-73
2-98,1-97
30-30,31-35
1-14,2-99
11-93,10-11
3-77,7-78
41-56,40-55
26-92,97-97
96-98,17-83
95-99,8-94
62-92,47-61
4-93,3-61
3-86,2-16
80-99,21-96
6-90,47-91
6-6,7-91
13-91,13-13
53-91,52-94
1-86,4-86
53-90,54-90
7-62,7-62
11-89,11-90
17-18,18-92
14-35,11-34
6-99,19-98
35-59,34-59
65-82,68-83
2-95,3-38
2-92,3-92
51-52,43-52
9-41,9-49
8-97,8-31
20-67,66-97
4-87,37-86
20-21,20-89
16-87,15-88
59-68,60-68
46-83,86-90
15-55,9-16
3-8,7-79
96-98,41-92
16-75,62-82
79-99,62-78
1-96,97-97
46-60,45-45
68-68,41-67
22-87,21-86
80-81,80-84
1-30,31-50
49-91,49-90
8-74,73-78
32-32,31-40
3-89,4-89
50-61,51-61
36-81,35-37
4-97,4-98
39-87,38-87
8-98,98-99
4-96,95-97
1-53,3-87
19-20,19-90
13-78,12-79
1-2,2-75
2-42,2-90
3-16,3-23
32-72,87-98
57-88,60-88
15-28,29-29
3-4,4-99
48-48,46-50
91-94,87-95
16-97,98-99
38-38,48-94
30-63,7-62
7-98,8-8
4-50,49-50
17-49,18-18
7-75,6-6
70-88,73-87
3-45,3-44
87-94,36-48
66-73,13-65
34-75,8-73
2-53,2-82
1-85,71-86
2-7,6-88
2-4,4-93
20-20,13-19
38-71,26-71
14-88,20-88
57-81,56-82
13-85,6-56
34-74,34-34
64-64,25-63
34-35,34-35
18-29,13-29
39-40,15-41
44-71,43-70
39-75,37-37
18-78,18-90
49-50,49-94
17-96,1-16
45-56,48-55
48-79,48-80
6-93,5-93
12-34,11-99
19-22,18-19
3-76,2-65
8-99,94-97
37-42,37-42
20-88,17-92
15-49,15-45
11-35,11-34
7-98,3-8
61-92,4-58
6-99,42-95
5-86,45-87
67-77,67-77
80-99,38-98
58-99,22-89
26-88,26-88
29-89,8-28
47-96,40-96
57-95,34-83
9-99,98-99
19-73,18-73
5-5,5-82
46-46,25-45
17-18,17-50
56-61,56-62
1-71,71-71
25-39,24-25
9-55,54-54
6-96,95-95
22-85,21-80
6-86,4-6
15-81,78-81
4-96,5-95
56-65,56-56
43-96,42-43
18-79,80-95
54-80,21-79
32-32,31-92
75-75,76-95
30-96,29-69
55-56,55-97
14-96,13-95
25-99,27-99
40-96,41-96
20-20,19-71
10-44,11-44
53-53,51-58
9-82,10-10
48-79,47-79
28-88,9-60
94-95,46-95
66-98,8-97
34-91,33-91
36-92,11-88
20-59,49-86
3-86,4-76
25-73,36-72
19-19,18-93
7-88,4-5
77-91,90-90
45-86,45-45
8-31,30-40
35-43,44-61
44-45,44-74
40-96,56-59
56-71,57-80
42-75,41-77
18-18,19-22
25-25,26-26
19-20,20-68
72-98,64-73
24-76,73-77
39-83,38-39
87-94,84-88
3-98,1-2
43-63,44-62
52-54,53-57
16-29,53-76
4-63,79-86
30-86,75-96
42-42,36-41
6-6,5-67
3-4,6-80
20-32,38-74
28-53,28-29
24-91,95-99
78-85,79-79
30-69,14-69
15-83,11-84
2-89,1-3
93-98,72-80
3-65,2-65
26-79,25-78
57-91,57-58
19-19,18-19
18-96,17-19
41-71,1-72
13-41,10-21
18-24,20-24
83-83,84-95
57-89,52-94
1-97,1-97
28-92,27-27
6-73,18-71
5-99,4-23
39-76,96-99
29-57,29-58
85-85,15-86
90-93,90-96
41-78,42-78
23-97,24-96
27-84,26-84
4-69,78-84
59-88,59-89
8-75,8-90
56-56,50-56
32-72,54-71
64-92,63-89
40-66,66-66
4-41,14-40
98-99,3-99
2-3,10-65
65-81,42-64
15-77,71-75
33-59,24-32
50-76,31-49
31-50,30-51
36-89,12-88
38-87,37-88
64-89,64-88
13-89,7-13
44-77,43-78
67-83,3-53
7-46,7-8
1-52,28-53
7-7,8-75
54-66,53-53
73-73,35-74
80-84,80-82
1-99,2-99
22-97,52-96
75-78,62-87
45-98,23-82
61-66,61-69
6-7,7-99
69-75,75-76
1-76,3-77
2-99,3-11
77-99,77-83
5-42,25-76
60-89,35-88
7-98,5-97
77-86,67-85
85-96,23-47
16-23,15-20
26-91,26-90
12-15,12-29
45-95,32-48
38-42,37-42
6-95,2-7
3-90,4-90
78-86,86-86
7-88,7-79
2-94,3-94
34-99,8-97
2-3,2-53
33-65,34-34
9-9,10-10
27-29,28-65
8-95,94-96
44-46,45-74
17-82,63-82
17-73,74-74
2-3,1-3
24-25,25-72
95-95,10-95
13-21,13-18
96-96,94-97
64-91,65-92
11-13,1-12
19-74,99-99
97-97,5-96
49-80,50-80
20-68,21-87
14-91,13-13
8-94,1-5
64-91,61-63
1-2,2-89
39-80,38-73
31-56,15-75
90-90,90-97
11-11,15-43
28-81,52-81
29-99,29-99
31-36,32-92
1-99,28-99
6-10,6-21
64-90,54-89
88-95,2-93
87-88,47-88
92-92,79-91
11-91,3-96
18-19,18-65
12-79,1-78
39-66,65-65
53-54,54-62
23-57,23-78
16-94,17-93
17-98,99-99
32-34,33-96
68-72,73-73
1-2,1-95
8-93,9-92
56-61,57-61
3-94,3-94
2-4,6-74
2-94,94-94
5-75,3-5
88-90,64-92
70-97,63-97
5-99,4-99
91-97,3-96
5-46,2-2
2-2,5-91
23-60,23-60
9-39,10-39
9-25,10-54
4-47,3-47
43-52,49-69
27-27,27-27
6-94,5-94
8-9,8-68
53-78,52-53
16-73,15-73
65-77,5-76
60-96,60-92
49-55,39-56
33-59,15-60
6-79,6-94
3-3,4-99
17-88,18-59
98-99,97-97
5-36,35-97
97-98,31-35
23-23,22-76
8-94,9-93
38-38,37-86
1-24,4-47
7-97,8-98
22-70,71-71
2-49,3-3
70-86,76-98
91-98,46-97
37-65,61-69
39-39,25-38
1-76,1-2
71-87,89-98
46-47,38-42
12-13,20-95
4-58,13-59
48-80,45-99
91-93,33-90
6-57,6-6
16-19,15-18
70-72,1-73
3-82,3-83
76-89,76-89
57-96,58-95
35-64,36-65
2-92,93-93
95-98,10-83
3-5,17-63
37-69,36-63
5-49,37-49
75-82,20-74
2-65,1-3
4-5,4-31
72-73,71-74
4-75,74-95
30-31,30-99
36-60,36-59
4-6,13-95
8-54,5-6
57-94,58-97
5-6,6-99
88-88,70-87
16-17,17-56
3-94,2-94
24-58,23-25
22-48,23-91
18-59,19-92
9-10,8-88
33-82,6-66
42-61,42-85
2-3,4-58
13-15,12-20
31-94,31-31
63-93,31-93
4-76,17-76
45-76,45-60
13-89,9-88
75-77,51-76
26-26,26-92
1-27,21-69
4-89,1-94
32-95,36-94
38-83,89-89
15-16,16-80
73-84,98-98
28-75,27-28
25-27,9-26
16-98,17-99
10-72,91-92
40-91,51-92
29-29,7-30
30-45,22-46
13-95,4-4
43-59,29-59
12-41,40-55
4-92,91-98
25-25,24-35
8-16,7-13
9-99,9-98
28-87,27-28
14-53,13-54
67-81,6-42
29-99,30-95
5-37,5-6
95-95,10-94
12-95,13-96
16-21,17-17
17-25,18-48
81-81,27-80
78-84,77-91
24-33,23-32
1-99,1-98
16-83,17-83
85-91,86-86
59-88,89-99
40-99,98-98
42-90,23-42
20-64,55-65
9-89,90-90
99-99,8-97
92-95,4-88
32-81,10-82
4-4,4-63
1-10,4-93
58-59,8-59
51-54,51-69
10-82,4-9
82-96,69-76
63-81,62-82
72-73,70-74
69-88,69-88
31-73,3-98
39-57,46-54
29-29,30-57
94-99,10-95
4-96,3-4
11-96,12-96
59-61,58-58
58-58,58-68
2-95,2-38
9-13,10-31
7-54,53-91
16-33,36-75
53-77,53-76
33-34,33-90
6-61,69-94
5-81,5-82
2-93,92-93
1-29,8-97
5-5,5-89
42-80,41-80
2-96,33-99
17-57,17-56
11-74,11-75
39-60,39-40
33-34,33-56
36-75,35-37
68-77,68-77
22-95,21-96
61-62,58-61
33-92,64-98
5-95,5-96
10-75,11-40
5-70,6-70
10-86,9-10
5-93,5-6
70-98,21-95
76-99,46-75
20-21,21-54
24-47,65-98
32-62,32-44
46-92,45-73
52-59,51-57
2-92,3-3
46-56,47-57
90-90,24-89
23-69,75-89
6-73,73-77
16-17,17-80
89-89,26-88
36-56,38-83
55-70,13-54
14-50,77-89
22-27,34-81
69-77,73-76
4-83,5-5
13-54,13-55
61-61,60-98
98-98,23-97
21-44,20-44
62-75,32-71
51-93,49-80
53-64,43-65
52-91,52-67
5-89,2-88
4-86,3-98
32-72,32-64
52-78,79-86
28-94,21-50
43-61,46-59
75-76,75-83
12-43,11-12
72-73,1-58
8-94,7-8
5-92,4-93
54-62,41-62
43-43,43-75
34-81,38-82
9-99,10-98
15-89,91-91
12-59,13-60
21-76,21-76
25-63,24-62
17-82,92-95
22-49,33-49
8-34,4-9
21-43,22-40
4-99,1-3
7-41,41-68
3-74,74-75
6-80,1-5
35-85,36-70
7-99,12-17
8-95,7-94
12-80,12-80
3-96,4-97
22-69,69-70
24-35,25-51
22-51,23-51
36-63,10-37
1-99,34-98
23-89,37-88
73-73,72-76
35-91,35-36
6-24,3-12
6-6,7-91
28-52,55-77
51-78,50-77
2-3,3-96
30-97,30-98
32-70,9-70
95-96,1-94
20-99,20-21
68-79,6-68
17-64,39-51
21-58,56-57
14-25,13-14
16-96,97-99
98-99,70-97
1-35,10-66
12-56,9-13
47-84,19-63
73-89,72-90
52-95,12-95
3-95,1-3
78-92,93-93
99-99,4-97
46-86,29-45
79-95,78-96
87-92,93-95
22-81,22-80
32-33,33-71
53-99,53-83
21-98,20-97
93-94,6-93
78-96,10-77
1-91,56-69
60-98,51-60
20-20,21-32
95-96,96-96
4-26,5-27
85-92,42-84
86-86,72-85
20-25,24-30
11-28,12-70
23-70,8-8
7-7,7-74
48-93,49-92
4-72,71-72
15-28,14-32
25-73,47-74
67-77,67-68
10-84,66-84
7-96,7-96
2-29,29-38
52-52,52-97
72-73,72-95
20-50,51-51
1-86,46-87
13-36,14-20
60-98,53-98
6-40,7-41
62-76,41-75
8-38,37-51
14-90,13-91
1-54,18-53
19-94,94-96
77-95,76-85
3-98,2-3
24-93,48-92
10-98,97-99
1-5,3-4
15-15,16-86
26-93,23-79
8-48,9-9
38-98,32-55
30-60,31-48
15-69,16-70
9-79,9-10
53-90,52-72
9-28,27-97
3-91,2-3
10-42,42-50
2-79,8-79
4-44,73-94
4-89,1-88
51-53,52-92
22-36,35-81
72-96,71-96
69-69,70-89
20-97,19-20
33-99,34-34
31-33,32-94
7-22,23-80
2-4,3-41
73-79,24-80
95-97,99-99
6-21,2-7
3-94,4-95
7-87,86-86
9-80,8-80
4-20,3-7
54-89,54-88
22-90,32-90
91-98,61-91
18-69,32-52
7-17,8-66
35-58,12-57
4-87,85-86
2-99,94-99
33-95,32-33
42-42,2-41
28-43,28-29
23-72,23-71
6-92,6-91
32-56,33-97
83-96,83-86
32-67,33-33
26-40,26-39
11-96,10-11
7-89,6-90
12-46,19-47
39-95,40-99
89-89,79-88
19-71,6-18
47-93,46-94
39-77,28-32
7-70,8-69
72-73,73-94
51-63,48-61
7-88,29-87
67-85,35-86
1-82,17-83
68-86,87-87
42-43,43-44
46-72,68-73
35-37,23-78
1-39,35-40
12-96,13-33
21-94,20-93
36-53,8-99
35-83,28-36
99-99,35-89
16-40,15-25
28-39,39-40
56-88,55-75
5-86,87-94
67-70,46-69
25-67,66-68
73-73,13-72
30-41,66-81
32-90,31-91
9-33,4-7
25-48,12-59
21-96,16-22
5-35,29-41
50-50,44-76
19-86,18-87
9-49,9-47
7-70,9-95
23-93,94-97
24-72,38-71
7-83,4-8
72-90,42-73
7-93,92-92
91-91,7-92
72-73,71-72
4-96,1-1
7-76,76-90
29-92,27-30
1-97,2-99
29-72,59-73
6-18,1-4
74-93,74-74
12-97,11-66
23-27,10-25
64-95,63-64
34-41,42-47
24-71,70-72
23-37,24-38
22-95,94-99
40-63,48-64
35-65,36-66
14-15,14-41
1-71,1-47
5-28,14-29
92-92,90-91
34-35,34-42
58-61,54-60
60-73,61-94
11-15,14-49
3-8,13-98
3-50,4-49
""".trimIndent()
