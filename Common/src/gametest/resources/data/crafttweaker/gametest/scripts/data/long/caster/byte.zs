import crafttweaker.api.data.IData;

var data = 1L as IData;
storeData(data);
println((data as byte) == (1 as byte));