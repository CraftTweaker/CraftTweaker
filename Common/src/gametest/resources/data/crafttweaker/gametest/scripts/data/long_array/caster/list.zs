import crafttweaker.api.data.IData;
import stdlib.List;

var data = [1L, 2L] as IData;
storeData(data);
var list = data as List<IData>;
for item in list {
    println(item as string);
}
