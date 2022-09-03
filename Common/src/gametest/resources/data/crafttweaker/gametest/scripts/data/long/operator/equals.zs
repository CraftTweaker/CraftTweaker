import crafttweaker.api.data.IData;

var first = 8L as IData;
var second = 8L as IData;
storeData(first);
storeData(second);
println(first == second);