import crafttweaker.api.data.IData;
import stdlib.List;

var data = [1, 2] as IData;
storeData(data);
var list = data as List<IData>;
for item in list {
    println(item as string);
}
