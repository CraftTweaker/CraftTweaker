import crafttweaker.api.data.IData;
import stdlib.List;

var data = [1 as byte, 2 as byte] as IData;
storeData(data);
var list = data as List<IData>;
for item in list {
    println(item as string);
}
