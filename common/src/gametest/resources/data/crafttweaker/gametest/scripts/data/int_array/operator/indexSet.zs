import crafttweaker.api.data.IData;

var data = [1, 2] as IData;
storeData(data);
data[0] = 3;
println(data[0] as int);