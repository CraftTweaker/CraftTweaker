import crafttweaker.api.item.IItemStack;
import crafttweaker.api.loot.conditions.LootConditionBuilder;
import crafttweaker.api.loot.conditions.vanilla.DamageSourceProperties;
import crafttweaker.api.loot.conditions.vanilla.KilledByPlayer;
import crafttweaker.api.loot.conditions.vanilla.WeatherCheck;
import crafttweaker.api.loot.modifiers.CommonLootModifiers;

import stdlib.List;

// ========= LOOT MODIFIERS =========

// Welcome to one of the probably most complicated yet powerful components of
// CraftTweaker: Loot Modifiers.
// Loot Modifiers allow you to alter the drops of basically everything that
// uses loot tables: block drops, entity drops, dungeon loot...
// Great power comes at a cost: complexity. While some basic and common usage
// functionality has been simplified, some slightly more advanced needs require
// the usage of more advanced techniques.
// Nevertheless, we'll start with the basics

// ---- PRINTING ----
// First off, CraftTweaker allows you to print the names of all loot modifiers
// that are currently registered. This is useful to know what mods are adding
// modifiers by default or even just checking if your changes are being
// applied.
// To print all loot modifiers you can use the 'getAllNames' method as follows:

for modifierName in loot.modifiers.getAllNames() {
    print(modifierName);
}

// This will output a list that roughly follows the following format in the
// logs:
// [15:53:55.458][DONE][CLIENT][INFO] examplemod:custom_glm

// Don't worry if you don't see anything pop up: it may just mean that there
// aren't any loot modifiers registered.

// ---- REMOVAL ----
// Removing a loot modifier has never been easier: this will allow you to get
// rid of the changes other mods (or even yourself!) are applying via loot
// modifiers.
// There are various alternatives to removing loot modifiers:
// - a single loot modifier by name;
// - all loot modifiers added by a mod;
// - all loot modifiers whose name matches a regular expression;
// - all loot modifiers in general.

// To remove a single loot modifier, you need to know its name. This is
// generally its JSON name, which can be discovered by opening the JAR file of
// the mod. If you aren't sure about the correct name, then refer to the
// PRINTING section of this example to find out all loot modifier names.
// After you get a hold of the name, use the 'removeByName' method.
// The following example removes the Loot Modifier with name 'custom_glm'
// added by mod 'examplemod'.

loot.modifiers.removeByName("examplemod:custom_glm");

// Removing all loot modifiers added by a mod is similar, except it uses
// 'removeByModId'. The following example will remove all loot modifiers added
// by mod 'examplemod':

loot.modifiers.removeByModId("examplemod");

// Removing by regex works the same way, with 'removeByRegex':

loot.modifiers.removeByRegex("^[a-z]*:(glm)[a-z]*$");

// This would match all loot modifiers added by any mod ID with only letters in
// its name (the first [a-z]*), and whose name starts with "glm" and is then
// followed solely by lowercase letters. As an example, the following two
// names would be removed:
//  - forge:glmyesthisisaname
//  - immersiveengineering:glmtest
// The following wouldn't:
//  - crafttweaker:my_glm
//  - mymod:glm_hello123
//  - more-blocks:glmhello

// Last but not least, removing everything works too, and gives you a clean
// slate from which you can start building. For that, use 'removeAll':

loot.modifiers.removeAll();

// ---- GETTING ----
// It is always possible to get a list of all loot modifiers or a specific one
// for usage in other functions. An example may be replacing a loot modifier
// of a mod by adding some more conditions on top of it. To do that, you can
// use the 'getByName', 'getAllNames', and 'getAll' methods.

val customGlm = loot.modifiers.getByName("examplemod:custom_glm");
val allKnownModifiers = loot.modifiers.getAll();
val allKnownModifierNames = loot.modifiers.getAllNames();

// ---- ADDING ----
// Now we come to the more spicy part of the loot modifier matter: adding custom
// ones. Now, to do that we need to understand that loot modifiers are nothing
// more than functions that take a list containing all IItemStacks that have
// been generated and a LootContext. The LootContext is effectively a class that
// allows you to know how exactly that loot was generated. Was it due to the
// death of an entity? A block being broken with a wooden axe by your friend?
// An explosion occurring 3.5 blocks away? Everything that can be known or
// inferred is provided to you for use.

// There are various ways of adding, or registering, loot modifiers. The easiest
// way is by combining the modifiers of CommonLootModifiers and the extensions
// provided on blocks, entities, items, etc. Refer to the docs for a complete
// list of what is allowed and what isn't. Since this isn't the scope for this
// quick example, the following is a simple example that makes sand drop ender
// eyes if broken with a stick:

<block:minecraft:sand>.addToolDrop(
    "sticky_ender_eyes",
    <item:minecraft:stick>,
    <item:minecraft:ender_eye>
);

// Another simpler way to register a loot modifier is using the
// 'registerUnconditional' method. The method accepts a name, which must be
// unique and identify that specific loot modifier, and a function that acts as
// the loot modifier itself.

loot.modifiers.registerUnconditional("add_diamonds_1", (drops, ctx) => {
    // This loot modifier effectively adds 2 diamonds to every drop of every
    // possible thing, be it an entity dying, a block being broken...
    drops.add(<item:minecraft:diamond> * 2);
    return drops;
});

// Adding loot to the drops of a loot table is very common, so the above code
// can be effectively replaced with a shorter equivalent that uses already
// provided functionality:

loot.modifiers.registerUnconditional(
    "add_diamonds_2",
    CommonLootModifiers.add(<item:minecraft:diamond> * 2)
);

// This is not that useful, we want to restrict loot modifiers to a specific
// subset of events. This is where conditions come into play. While it is
// perfectly possible to check conditions within the loot modifier function
// itself, it is generally suggested to provide them separately. This way the
// game can optimize how loot modifiers work and avoid invoking the ones that
// do not match, thus saving processor cycles and overall making the game run
// better and faster.
// All conditions are functions that take in a LootContext and decide whether a
// specific loot modifier can be applied or not. We can either create our own
// completely custom loot conditions, or use the ones that vanilla already
// provides. It is generally suggested to use vanilla functions since the game
// already knows about them, so it can do some additional mangling and
// optimization if needed.
// To register a loot modifier in this case, we use the default 'register'
// method, passing it a loot condition builder, which is a tool that allows us
// to specify more than one condition in an easier way, and our function.

// What follows is an example that shows a basic usage of conditions:

loot.modifiers.register(
    // The name, as explained above
    "its_raining_cats_and_dogs",
    // We create a single condition, which is a 'WeatherCheck', i.e. the weather
    // must be in a certain way for this loot modifier to pass.
    LootConditionBuilder.createForSingle<WeatherCheck>((condition) => {
        // Namely, we require rain to be present
        condition.withRain();
    }),
    // If the conditions match, then we add 5 ghast tears to the loot
    CommonLootModifiers.add(<item:minecraft:ghast_tear> * 5)
);

// And now, for a more complicated example

loot.modifiers.register(
    // The name, as explained above
    "player_be_brave",
    // Here we start building our loot conditions
    LootConditionBuilder.create()
        // We require the entity to have been killed by a player
        .add<KilledByPlayer>()
        // and we specify additional details on how the killing must have
        // occurred.
        .add<DamageSourceProperties>((condition) => {
            // This duplication is required due to vanilla using the same
            // criteria as advancements for loot modifiers, meaning we need to
            // bridge the gap between an advancement criteria and a loot
            // condition
            condition.withPredicate((dsPredicate) => {
                // The entity must be killed directly in a melee fight
                // (so no spikes, arrows...)
                dsPredicate.withDirectEntityPredicate((dePredicate) => {
                    // The killer must be equipped with some stuff (i.e. it
                    // needs to have something as armor or in hand)
                    dePredicate.withEquipmentPredicate((eePredicate) => {
                        // Namely, it needs to have something in his main hand,
                        // the one it used for attacking
                        eePredicate.withItemInHand((iPredicate) => {
                            // And that needs to be a wooden axe, no matter
                            // the damage or any other particular NBT tag
                            // that may be attached to it.
                            iPredicate.withItem(<item:minecraft:wooden_axe>);
                        });
                    });
                });
            });
        }),
    // If the above conditions match, then
    (stacks, context) => {
        // Create a new list that will host all our drops
        val newList = new List<IItemStack>();
        // Then fill it with the original stacks multiplied by 64
        for item in stacks {
            newList.add(item * 64);
        }
        // And state this is the result
        return newList;
    }
);

// Woo, that was a lot to type. But now I hope you can see the inherent
// complexity of what loot modifiers allow you to do: you can manipulate and
// check almost every aspect of what caused the loot to drop and everything
// related either directly or indirectly to it. Fortunately, not all loot
// conditions have to be this deep and require this level of manipulation.

// I STRONGLY suggest you to go and take a look at the documentation and how-tos
// on the CraftTweaker docs to familiarize yourself with how loot modifiers and
// loot conditions interoperate.

// Happy tweaking!
