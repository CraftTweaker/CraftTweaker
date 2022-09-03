import crafttweaker.api.data.IData;

var data = [1L, 2L] as IData;
storeData(data);
data[0] = 3L;
println(data[0] as long);