import crafttweaker.api.data.IData;
import crafttweaker.api.data.IntArrayData;
var data = new IntArrayData([1, 2]);
storeData(data);
data.remove(0);
println(1 in data);