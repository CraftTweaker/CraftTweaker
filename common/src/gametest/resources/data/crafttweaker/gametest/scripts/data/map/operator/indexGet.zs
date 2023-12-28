import crafttweaker.api.data.MapData;
import crafttweaker.api.data.IData;

var data = new MapData({"key": "value" as IData, "1": "1 value" as IData, "second": "second value" as IData});
storeData(data);

println(data["second"] as string);
println(data[1] as string);
