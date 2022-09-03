import crafttweaker.api.data.IData;

var first = 2 as IData;
var second = 1 as IData;
storeData(first);
storeData(second);
println((first >> second) as int);