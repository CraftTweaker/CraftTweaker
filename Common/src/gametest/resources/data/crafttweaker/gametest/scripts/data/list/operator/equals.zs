import crafttweaker.api.data.ListData;
import crafttweaker.api.data.IData;

var first = new ListData([1, 2]);
var second as IData = new ListData([1, 2]);
storeData(first);
println(first == second);