import crafttweaker.api.data.ListData;

var data = new ListData([1L, 2L]);
storeData(data);
var array = data as long[];
for i in array {
    println(i);
}