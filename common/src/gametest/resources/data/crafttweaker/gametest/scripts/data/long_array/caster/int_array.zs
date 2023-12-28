import crafttweaker.api.data.IData;

var data = [1L, 2L] as IData;
storeData(data);
var array = data as long[];
for i in array {
    println(i);
}