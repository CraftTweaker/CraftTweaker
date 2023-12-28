import crafttweaker.api.data.IData;

var first = (1 as byte) as IData;
var second = (2 as byte) as IData;
storeData(first);
storeData(second);
println((first | second) as byte);