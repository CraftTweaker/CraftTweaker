import crafttweaker.api.data.IntArrayData;
import crafttweaker.api.data.IData;

var data = new IntArrayData([1, 2]);
storeData(data);

for num in data {
    println(num as int);
}