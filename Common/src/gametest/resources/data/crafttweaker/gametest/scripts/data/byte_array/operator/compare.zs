import crafttweaker.api.data.IData;

var first = [1 as byte, 2 as byte] as IData;
var second = [3 as byte] as IData;
storeData(first);
storeData(second);
println(first < second);