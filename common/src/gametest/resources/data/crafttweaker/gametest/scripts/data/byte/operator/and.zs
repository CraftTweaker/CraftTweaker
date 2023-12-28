import crafttweaker.api.data.IData;

var data = (2 as byte) as IData;
storeData(data);
println((data & 1) == 0);