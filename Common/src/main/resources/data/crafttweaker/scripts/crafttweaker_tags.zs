import crafttweaker.api.tag.MCTag;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.ingredient.IIngredient;
import crafttweaker.api.item.ItemDefinition;
import stdlib.List;

/*
    Tags are a method of grouping registry objects together (items, blocks, entity_types, and fluids, to name a few).

    Tags are commonly used in recipes to group variants of items together, such as being able to mix Oak planks and Birch planks to make sticks.
    However, Tags are not limited to recipes, and have some very interesting use cases outside of recipes.

    Some interesting use cases for tags are:
    - <tag:blocks:minecraft:bamboo_plantable_on> lets you change what blocks bamboo can be placed on.
    - <tag:blocks:minecraft:climbable> lets you mark blocks as climbable, allowing mobs to climb them like a ladder.
    - <tag:blocks:minecraft:enderman_holdable> lets you make a block holdable by an enderman.
    - <tag:blocks:minecraft:mineable/axe>, <tag:blocks:minecraft:mineable/hoe>, <tag:blocks:minecraft:mineable/pickaxe>, <tag:blocks:minecraft:mineable/shovel> let you make blocks mineable with different tools.
    - <tag:entity_types:minecraft:axolotl_always_hostiles> lets you make axolotl always attach an entity type.
    - <tag:items:minecraft:freeze_immune_wearables> lets you make wearing certain armor items make the player immune to freezing from powder snow.

    There are many more utility tags like the one shown above; you can find all of the tags by using '/ct dump tag' and looking in the 'crafttweaker.log' file or '/ct dump_brackets' and looking in the 'ct_dumps/tag.txt' file.

    While the game currently only registers tags for:
    - Items
    - Blocks
    - Entity Types
    - Fluids
    - Game Events
    - Biomes

    There are a plethora of other types of tags that the game supports.
    You can find a full list by doing '/ct dump tagmanager' and looking in the 'crafttweaker.log' file or '/ct dump_brackets' and looking in the 'ct_dumps/tagmanager.txt' file.

    In CraftTweaker you can:
    - Create new completely new tags.
    - Add elements to existing tags added by the game, Forge, Fabric or any other datapack you may have loaded.
    - Remove elements from existing tags added by the game, Forge, Fabric or any other datapack you may have loaded.

    Some tags, such as 'biome', 'game_event' and 'dimension_type', may not have their elements be exposed to scripts, or are just not easily accessible to scripters to be able to add or remove them from a tag.
    To solve this, CraftTweaker allows you to add and remove from tags using the name of an element, for example instead of writing:
    <tag:items:minecraft:wool>.remove(<item:minecraft:white_wool>);
    You can write:
    <tag:items:minecraft:wool>.removeId(<resource:minecraft:white_wool>);

    Another example, if you wanted to add to a biome tag, you would have to do:
    <tag:worldgen/biome:minecraft:has_structure/shipwreck_beached>.addId(<resource:minecraft:plains>);
    and
    <tag:worldgen/biome:minecraft:has_structure/shipwreck_beached>.removeId(<resource:minecraft:beach>);

    This system gives you the full power of tags that the datapack system provides.

    Now that you hopefully have a basic understanding of Tags and what they can do, the rest of this file will focus on showing examples of creating, modifying, and interacting with Tags.
*/

// Adding to existing Tags

// Adds Dirt to <tag:items:minecraft:planks>, allowing it to be used in place of Planks in recipes.
<tag:items:minecraft:planks>.add(<item:minecraft:dirt>);

// Adds Diamond Blocks to <tag:blocks:minecraft:bamboo_plantable_on>, allowing bamboo to be placed on it.
<tag:blocks:minecraft:bamboo_plantable_on>.add(<block:minecraft:diamond_block>);

// Adds Skeletons to <tag:entity_types:minecraft:axolotl_always_hostiles>, making Axolotl attack skeletons.
<tag:entity_types:minecraft:axolotl_always_hostiles>.add(<entitytype:minecraft:skeleton>);


// Removing from existing Tags

// Removes Leather Helmet from <tag:items:minecraft:freeze_immune_wearables>, removing it's ability to prevent freezing.
<tag:items:minecraft:freeze_immune_wearables>.add(<item:minecraft:leather_helmet>);

// Removes Diamond Blocks from <tag:blocks:minecraft:beacon_base_blocks>, making it that it can't be a Beacon Base.
<tag:blocks:minecraft:beacon_base_blocks>.remove(<block:minecraft:diamond_block>);

// Removes Rabbits from <tag:entity_types:minecraft:powder_snow_walkable_mobs>, removing their ability to walk on Powder Snow.
<tag:entity_types:minecraft:powder_snow_walkable_mobs>.remove(<entitytype:minecraft:rabbit>);

// Removes Snowy Taiga from <tag:worldgen/biome:minecraft:has_structure/igloo>, making it that Igloos will never spawn in the biome..
<tag:worldgen/biome:minecraft:has_structure/igloo>.removeId(<resource:minecraft:snowy_taiga>);


// Creating new Tags
// Tags need to be created before they can be used in a recipe, if you are splitting your Tag creation into a separate script, make sure that the script is loaded before you use it by using the priority preprocessor system.
// A tag is created once an element is added to it, for example:

<tag:items:crafttweaker:pickaxe>; // Does not create the Tag.
<tag:items:crafttweaker:pickaxe>.add(<item:minecraft:diamond_pickaxe>); // Creates the Tag and adds a Diamond to it.
<tag:items:crafttweaker:pickaxe>.add(<item:minecraft:iron_pickaxe>); // Adds Iron Pickaxe to the Tag that was created by the line above.

// Creating a new Menu Tag and adding a Menu to it:
// Note, Menu tags are currently not used in the game, so while this will create the Tag and add the Furnace menu to it, it won't actually do anything in game.
<tag:menu:crafttweaker:custom_menus>.addId(<resource:minecraft:furnace>);


// Iterating Tags
// If a Tag's element type is exposed to scripters, then you can iterate over it with a for loop like so:

for item in <tag:items:minecraft:wool> {

    println(item.commandString);
}

// If the Tag has elements which are not exposed to scripters, then you will need to do the following:

for id in <tag:items:minecraft:wool>.idElements {

    println(id);
}

// Using Tags in recipes

// Item Tags can be used in recipes just like Item's can, like so:
craftingTable.addShapeless("tag_example_recipe", <item:minecraft:diamond>, [<tag:items:minecraft:logs>, <item:minecraft:dirt>, <tag:items:minecraft:rails>]);

// Tags, however are not IIngredients, so to use IIngredient methods on a Tag, you will need to cast it yourself by doing either:
// - <tag:items:crafttweaker:pickaxe>.asIIngredient()
// - <tag:items:crafttweaker:pickaxe> as IIngredient // requires an import for 'crafttweaker.api.ingredient.IIngredient'
// The script line below will add a recipe that, when crafted, will damage the given element (either a Diamond Pickaxe or an Iron Pickaxe) of the <tag:items:crafttweaker:pickaxe> tag by 2.
craftingTable.addShapeless("ingredient_tag_example_recipe", <item:minecraft:cobblestone> * 3, [<item:minecraft:cobblestone_stairs>, <tag:items:crafttweaker:pickaxe>.asIIngredient().transformDamage(2)]);
