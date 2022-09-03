import crafttweaker.api.data.IData;

var data = true as IData;
storeData(data);
var bbyte = data as byte;

println(bbyte == 1 as byte);