import crafttweaker.api.data.ListData;
import crafttweaker.api.data.IData;

var data = new ListData([1, 2]);
storeData(data);
println(data[0] as int);