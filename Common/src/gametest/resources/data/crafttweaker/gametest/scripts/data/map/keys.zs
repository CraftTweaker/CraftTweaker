import crafttweaker.api.data.MapData;
import crafttweaker.api.data.IData;

var data = new MapData({"key": "value" as IData, "1": "value" as IData, "second": "value" as IData});
storeData(data);

for key in data.keys {
    println(key);
}
