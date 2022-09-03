import crafttweaker.api.data.IData;

var first = 1 as IData;
var second = 2 as IData;
storeData(first);
storeData(second);
println((first | second) as int);