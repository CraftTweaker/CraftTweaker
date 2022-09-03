import crafttweaker.api.data.IData;

var first = [1L, 2L] as IData;
var second = [1L, 2L] as IData;
storeData(first);
println(first == second);