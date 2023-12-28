import crafttweaker.api.data.IData;

var first = (1 as short) as IData;
var second = (1 as short) as IData;
storeData(first);
storeData(second);
println((first << second) as int);