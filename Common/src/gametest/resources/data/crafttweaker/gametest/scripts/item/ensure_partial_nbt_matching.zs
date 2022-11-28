val partialAll = <item:minecraft:apple>.withTag({});
val partialOnly = <item:minecraft:apple>.withTag({cool: "beans"});
val partialColl = <item:minecraft:apple>.withTag({coll: [1 as int]});

val actualSimple = <item:minecraft:apple>;
val actualHard = <item:minecraft:apple>.withTag({cool: "beans", coll: [1 as int, 2 as int, 3 as int]});
val actualExact = <item:minecraft:apple>.withTag({cool: "beans"});

println(partialAll.matches(actualSimple)); // false
println(partialAll.matches(actualHard)); // true
println(partialAll.matches(actualExact)); // true

println(partialOnly.matches(actualSimple)); // false
println(partialOnly.matches(actualHard)); // true
println(partialOnly.matches(actualExact)); // true

println(partialColl.matches(actualSimple)); // false
println(partialColl.matches(actualHard));  // true
println(partialColl.matches(actualExact)); // false