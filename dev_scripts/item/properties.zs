<item:minecraft:dirt>.burnTime = 500;

#onlyif modloader neoforge
<item:minecraft:blaze_rod>.setBurnTime(250, <recipetype:minecraft:smelting>);
<item:minecraft:blaze_rod>.setBurnTime(150, <recipetype:minecraft:blasting>);
<item:minecraft:blaze_rod>.setBurnTime(350, <recipetype:minecraft:smoking>);
#endif