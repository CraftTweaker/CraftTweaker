import crafttweaker.api.data.ListData;

var data = new ListData([1, 2]);
storeData(data);
var array = data as int[];
for i in array {
    println(i);
}