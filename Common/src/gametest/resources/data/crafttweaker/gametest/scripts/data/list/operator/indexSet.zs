import crafttweaker.api.data.ListData;
import crafttweaker.api.data.IData;

var data = new ListData([1, 2]);
storeData(data);
data[0] = 3;
println(data[0] as int);