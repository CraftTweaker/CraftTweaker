import crafttweaker.api.data.IData;

var first = [1, 2] as IData;
var second = [1, 2] as IData;
storeData(first);
println(first == second);