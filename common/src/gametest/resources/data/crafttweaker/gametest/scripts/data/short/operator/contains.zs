import crafttweaker.api.data.IData;

var first = (8 as short) as IData;
var second = (8 as short) as IData;
storeData(first);
storeData(second);
println(second in first);