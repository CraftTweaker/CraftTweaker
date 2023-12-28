import crafttweaker.api.data.IData;

var data = [1 as byte, 2 as byte] as IData;
storeData(data);
data[0] = 3;
println(data[0] as byte);