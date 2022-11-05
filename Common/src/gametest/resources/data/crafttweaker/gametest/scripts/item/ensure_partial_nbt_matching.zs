val partialAll = <item:minecraft:apple>.withTag({});
val partialOnly = <item:minecraft:apple>.withTag({cool: "beans"});
val partialColl = <item:minecraft:apple>.withTag({coll: [1 as int]});

val actualSimple = <item:minecraft:apple>;
val actualHard = <item:minecraft:apple>.withTag({cool: "beans", coll: [1 as int, 2 as int, 3 as int]});
val actualExact = <item:minecraft:apple>.withTag({cool: "beans"});

val partials = [ partialAll, partialOnly, partialColl ];
val actuals = [ actualSimple, actualHard, actualExact ];

for p in partials {
    for a in actuals {
        println(p.matches(a));
    }
}
