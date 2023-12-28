import crafttweaker.api.data.IData;

var first = [1L, 2L] as IData;
var second = [3L] as IData;
storeData(first);
storeData(second);
println(first < second);