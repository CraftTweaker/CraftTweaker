//There are two kinds of recipes: Shaped and shapeless
//Shaped means that the order of items in the crafting table matters (e.g. for tools)
//Shapeless means that the items can be anywhere in the crafting grid as long as all items are there (e.g. dye mixing)


//A shapeless recipe requires you to provide a name, output and inputs.
//As you can see, you can also return more than one item (in this case 10), but your ingredients all have to have a stack size of 1!
craftingTable.addShapeless("chop_ingot", <item:minecraft:gold_nugget> * 10, [<item:minecraft:gold_ingot>, <item:minecraft:flint>]);

//A shaped recipe, similar to a shapeless one, requires you to provide a name, output and inputs.
//Since crafting tables require you to specify where in the crafting grid the items need to go, here's a reference:
//In shaped recipes, unlike shapeless ones you may be required to state that a slot may accept "nothing". You do this by using <item:minecraft:air>

/*
  *************
  * a * b * c *
  *************
  * d * e * f *
  *************
  * g * h * i *
  *************

  becomes
  [[a, b, c], [d, e, f], [g, h, i]]
*/

//Here you can see, that you can use <item:minecraft:air> for an empty slot
//You can also see that this recipe only uses two rows. This means you can either craft this recipe in the first and second row in the crafting grid, or the second and third row.
craftingTable.addShaped("diamond_helmet_from_emeralds", <item:minecraft:diamond_helmet>, [[<item:minecraft:emerald>, <item:minecraft:emerald>, <item:minecraft:emerald>], [<item:minecraft:emerald>, <item:minecraft:air>, <item:minecraft:emerald>]]);


//You can also break it down into multiple lines as that may make it easier
craftingTable.addShaped("coal_compression", <item:minecraft:diamond_block>, [
    [<item:minecraft:coal_block>, <item:minecraft:coal_block>, <item:minecraft:coal_block>],
    [<item:minecraft:coal_block>, <item:minecraft:diamond>, <item:minecraft:coal_block>],
    [<item:minecraft:coal_block>, <item:minecraft:coal_block>, <item:minecraft:coal_block>]]);


//These recipes here will use ingredient transformers.
//That means that the items in the crafting grid will transform to something else.
//A well-known example of this is the filled bucket being transformed to an empty one.

//The transformDamage transformer causes the used Iron sword to take one point of damage whenever crafted
//Careful, this won't be affected by enchantments.
craftingTable.addShapeless("exmaple_transformer_anydamage_transform_damage", <item:minecraft:iron_nugget>, [<item:minecraft:iron_sword>.anyDamage().transformDamage()]);

//The reuse transformer causes the item to stay in the grid
//This recipe would allow you to extract an infinite number of diamonds when you place a diamond Sword in the crafting grid
craftingTable.addShapeless("example_transformer_reuse", <item:minecraft:diamond>, [<item:minecraft:diamond_sword>.reuse()]);