import crafttweaker.api.data.IData;

var data = [1, 2] as IData;
storeData(data);
var array = data as int[];
for i in array {
    println(i);
}