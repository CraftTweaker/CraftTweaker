import crafttweaker.api.data.ListData;

var data = new ListData(["first", "second"]);
storeData(data);
data.remove(0);

println("first" in data);