import crafttweaker.api.data.MapData;
import crafttweaker.api.data.IData;

var data = new MapData({"key": "value" as IData, "1": "value" as IData, "second": "value" as IData});
data.remove("second");
data.remove(1);
storeData(data);