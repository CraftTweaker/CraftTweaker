import crafttweaker.api.data.IData;

var data = [1 as byte, 2 as byte] as IData;
storeData(data);
data.remove(0);
println(1 in data);