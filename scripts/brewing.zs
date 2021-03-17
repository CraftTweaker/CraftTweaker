brewing.addRecipe(<item:minecraft:dirt>, <item:minecraft:apple>, <item:minecraft:arrow>);
brewing.addRecipe(<item:minecraft:glass>, <item:minecraft:diamond>, <item:minecraft:stick>); // doesn't exist
brewing.addRecipe(<item:minecraft:diamond>, <item:minecraft:dirt>, <item:minecraft:glass>); // doesn't exist
brewing.removeRecipe(<potion:minecraft:thick>, <item:minecraft:glowstone_dust>, <potion:minecraft:water>);
brewing.removeRecipeByReagent(<item:minecraft:golden_carrot>);
brewing.removeRecipeByOutputPotion(<potion:minecraft:swiftness>);
brewing.removeRecipeByInputPotion(<potion:minecraft:awkward>);
brewing.removeRecipe(<item:minecraft:glass>, <item:minecraft:diamond>, <item:minecraft:stick>);
brewing.removeRecipeByInput(<item:minecraft:glass>);