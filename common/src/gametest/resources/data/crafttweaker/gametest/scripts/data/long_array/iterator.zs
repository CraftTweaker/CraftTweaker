import crafttweaker.api.data.LongArrayData;
import crafttweaker.api.data.IData;

var data = new LongArrayData([1L, 2L]);
storeData(data);

for num in data {
    println(num as byte);
}