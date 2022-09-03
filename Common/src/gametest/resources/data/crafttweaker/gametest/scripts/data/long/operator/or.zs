import crafttweaker.api.data.IData;

var first = 1L as IData;
var second = 2L as IData;
storeData(first);
storeData(second);
println((first | second) as int);