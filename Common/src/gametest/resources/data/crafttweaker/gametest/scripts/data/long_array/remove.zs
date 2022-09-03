import crafttweaker.api.data.IData;

var data = [1L, 2L] as IData;
storeData(data);
data.remove(0);
println(1L in data);