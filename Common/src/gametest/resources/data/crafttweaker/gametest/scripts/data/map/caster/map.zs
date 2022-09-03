import crafttweaker.api.data.MapData;
import crafttweaker.api.data.IData;

var data = new MapData({"first key": "value" as IData});
storeData(data);

var map = data as IData[string];
for key, value in map {
    println(key);
}