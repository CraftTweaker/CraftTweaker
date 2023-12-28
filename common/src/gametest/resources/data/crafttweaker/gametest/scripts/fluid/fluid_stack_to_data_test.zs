import crafttweaker.api.data.IData;

var data = (<fluid:minecraft:water> * 2) as IData;
storeData(data);
println(data as string);