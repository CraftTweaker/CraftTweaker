import crafttweaker.item.IIngredient;

print("Hey");
// recipes.removeAll();


var planks = <ore:plankWood>;
var iron = <minecraft:iron_ingot>;

// var planks = <minecraft:coal>;

recipes.removeShaped(<minecraft:wooden_pressure_plate>);

recipes.addShaped(<minecraft:coal> * 3,    [[null, planks, null],
                                            [null, planks, null],
                                            [planks, planks, planks]]);

recipes.addShaped(<minecraft:diamond> * 3,    [[null, planks, null],
                                               	        	    [null, planks, null],
                                               		        	[planks, planks, planks]]);

recipes.addShaped(<minecraft:wooden_pressure_plate> * 3,       [[planks, planks, planks],
                                               		                            [null, planks, null],
                                               		                        	[planks, planks, planks]]);

recipes.addShapeless(<minecraft:diamond> * 10,    [iron, iron, iron, iron, iron, iron, iron, iron, iron]);
recipes.addShapeless(<minecraft:diamond> * 10,    [iron, iron, iron, iron, iron, iron, iron, iron, iron]);
recipes.addShapeless(<minecraft:diamond> * 20,    [iron, iron, iron, iron, iron, iron, iron, iron, iron]);

var irono = <ore:ingotIron>;
recipes.addShapeless(<minecraft:diamond> * 20,    [irono, irono, irono, irono, irono, irono, irono, irono, irono]);

// <ore:ingotIron>.add(<minecraft:dirt>);
recipes.addShapeless(<minecraft:diamond> * 20,    [irono, irono, irono, irono, irono, irono, irono, irono, irono]);


recipes.addShapeless(<minecraft:iron_ingot> * 3,    [planks, planks, planks]);


val wither = <potion:asd>;