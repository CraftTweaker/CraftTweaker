import crafttweaker.api.data.ListData;

var data = new ListData(["first", "second"]);
storeData(data);

for str in data {
    println(str as string);
}