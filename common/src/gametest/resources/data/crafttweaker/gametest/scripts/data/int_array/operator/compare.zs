import crafttweaker.api.data.IData;

var first = [1, 2] as IData;
var second = [3] as IData;
storeData(first);
storeData(second);
println(first < second);