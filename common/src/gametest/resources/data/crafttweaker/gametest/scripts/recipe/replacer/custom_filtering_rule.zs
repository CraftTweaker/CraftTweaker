import crafttweaker.api.recipe.replacement.Replacer;
import crafttweaker.api.recipe.replacement.type.CustomFilteringRule;
import crafttweaker.api.ingredient.IIngredient;

Replacer.create()
    .filter(CustomFilteringRule.of((holder) => holder.value.ingredients.length > 6))
    .replace<IIngredient>(
        <recipecomponent:crafttweaker:input/ingredients>,
        <targetingstrategy:crafttweaker:shallow>,
        <item:minecraft:diamond>,
        <item:minecraft:apple>
    )
    .execute();