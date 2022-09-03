import crafttweaker.api.data.ListData;
import crafttweaker.api.data.IData;
import stdlib.List;

var data = new ListData(["first", "second"]);
storeData(data);
var list = data as List<IData>;
for item in list {
    println(item as string);
}
