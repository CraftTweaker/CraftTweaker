import crafttweaker.api.data.MapData;
import crafttweaker.api.data.IData;

var first = new MapData({"key": "value" as IData});
var second as IData = new MapData({"key": "value" as IData});

println(first == second);