import crafttweaker.api.data.IData;

var first = [1 as byte, 2 as byte] as IData;
var second = [1 as byte, 2 as byte] as IData;
storeData(first);
println(first == second);