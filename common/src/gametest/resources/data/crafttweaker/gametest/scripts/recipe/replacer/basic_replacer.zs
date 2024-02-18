import crafttweaker.api.recipe.replacement.Replacer;
import crafttweaker.api.ingredient.IIngredient;

Replacer.create()
    .replace<IIngredient>(
        <recipecomponent:crafttweaker:input/ingredients>,
        <item:minecraft:diamond>,
        <item:minecraft:apple>
    )
    .execute();