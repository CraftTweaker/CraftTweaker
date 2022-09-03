import crafttweaker.api.data.MapData;
import crafttweaker.api.data.IData;

var data = {BlockEntityTag: {Items: [{Slot: 0, id: "minecraft:dirt", Count: 64}], id: "minecraft:shulker_box"}} as IData;

storeData(data);

println("BlockEntityTag" in data);
println({BlockEntityTag: {id: "minecraft:shulker_box"}} in data);