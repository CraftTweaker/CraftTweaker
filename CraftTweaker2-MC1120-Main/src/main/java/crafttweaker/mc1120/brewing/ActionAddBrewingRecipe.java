package crafttweaker.mc1120.brewing;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class ActionAddBrewingRecipe implements IBrewingAction {
    private final MultiBrewingRecipe recipe;
    private final String outName;
    private final boolean valid;

    public ActionAddBrewingRecipe(IIngredient input, IIngredient[] ingredients, IItemStack output, boolean hidden) {
        this.outName = output.toString();
        this.recipe = new MultiBrewingRecipe(input, ingredients, output, !hidden);
        this.valid = recipe.isValid();
    }


    @Override
    public void apply() {
        if (!valid) {
            CraftTweakerAPI.logError(String.format("Brewing recipe for %s is invalid", outName));
            return;
        }
        BrewingRecipeRegistry.addRecipe(recipe);
    }

    @Override
    public String describe() {
        return "Adding brewing recipe for " + outName + ", Registry size now: " + BrewingRecipeRegistry.getRecipes().size();
    }
}
