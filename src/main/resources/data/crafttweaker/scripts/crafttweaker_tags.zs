import crafttweaker.api.tag.MCTag;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.MCItemDefinition;
import stdlib.List;
//Tags are like a way of grouping more than one item in a same recipe.
//There's different types of tags: Item tags, block tags, fluid tags and entity tags.
//You can use both default tags (added by forge or mods) or your own custom ones. /ct dump tags will return the list of tags in your game to the CrT log.

//An example of an item tag is : "<tag:items:minecraft:planks>", which by default includes all different kinds of Vanilla wood types. It is very possible that
//a mod that adds new kinds of wood will add those into this Tag.

//Onto tweaking Tags. (Import just in case : crafttweaker.api.tag.MCTag)
//Tag type is very important! You cannot add Items onto a Block Tag or Fluids onto an Entity Tag!
//A BlockTag requires an MCBlock, An EntityTag requires an MCEntityType, An ItemTag requires an IItemStack or MCItemDefinition and a FluidTag requires a FluidStack or an MCFluid.
//Technically, the Item and Fluid tags require the definition, but to make scripts easier to read you can also provide the Stack.
//With the new changes to tags, now you have to specify the kind of tag. Notice the "<tag:items:forge:name>" or "<tag:fluids:crafttweaker:name>". If you wish to add a new tag, just write crafttweaker in between the tagtype and the name.
//Tags ignore NBT data.


//Tweaking existing tags:
//Let's say you are a heartless monster and you consider redstone to be a gem. But forge doesn't agree with you. So you get the forge tag for gems which is:
var forge_gems = <tag:items:forge:gems>;

//You can also print the elements forming it by using:
for tag in forge_gems.elements {
	println(tag.commandString); //Tags can implicitly cast to string using their commandString getter
}
/*Which prints to the log:
[22:04:39.878][DONE][SERVER][INFO] <item:minecraft:diamond>.definition
[22:04:39.878][DONE][SERVER][INFO] <item:minecraft:emerald>.definition
[22:04:39.879][DONE][SERVER][INFO] <item:minecraft:lapis_lazuli>.definition
[22:04:39.879][DONE][SERVER][INFO] <item:minecraft:prismarine_crystals>.definition
[22:04:39.879][DONE][SERVER][INFO] <item:minecraft:quartz>.definition
*/

//So if you wanted to add redstone to the gems you'd do:
forge_gems.add(<item:minecraft:redstone>);

//And by the same rule of thumb, if you wanted to remove prismarine crystals because you don't want them to be used when a real "Gem" is required, you could remove them from the tag with the remove method.
forge_gems.remove(<item:minecraft:prismarine_crystals>);

//Let's print again to see the contents of forge:gems after these changes:
for tag in forge_gems.elements {
	println(tag.commandString); //Tags can implicitly cast to string using their commandString getter
}
/* Which gives us:
[22:13:38.400][DONE][CLIENT][INFO] <item:minecraft:diamond>.definition
[22:13:38.400][DONE][CLIENT][INFO] <item:minecraft:emerald>.definition
[22:13:38.400][DONE][CLIENT][INFO] <item:minecraft:lapis_lazuli>.definition
[22:13:38.401][DONE][CLIENT][INFO] <item:minecraft:quartz>.definition
[22:13:38.401][DONE][CLIENT][INFO] <item:minecraft:redstone>.definition
*/

//As for custom tags, the moment you add something to a tag, if the tag doesnt exist it will be created. Make sure what you are adding and the tagType match!

//This tag will contain all pickaxes.
<tag:items:crafttweaker:all_pickaxes>.add(<item:minecraft:wooden_pickaxe>);
<tag:items:crafttweaker:all_pickaxes>.add(<item:minecraft:stone_pickaxe>);
<tag:items:crafttweaker:all_pickaxes>.add(<item:minecraft:iron_pickaxe>);
<tag:items:crafttweaker:all_pickaxes>.add(<item:minecraft:golden_pickaxe>);
<tag:items:crafttweaker:all_pickaxes>.add(<item:minecraft:diamond_pickaxe>);
<tag:items:crafttweaker:all_pickaxes>.add(<item:minecraft:netherite_pickaxe>);

var pickaxeAll = <tag:items:crafttweaker:all_pickaxes>;
//Then you can do something like 1 Cobblestone Stairs turns into 3 Cobblestone, but it needs a pickaxe, and it consumed 2 durability.

craftingTable.addShapeless("stairs_to_cobble", <item:minecraft:cobblestone> * 3, [<item:minecraft:cobblestone_stairs>, pickaxeAll.asIIngredient().transformDamage(2)]);

//Have fun with your tags!
