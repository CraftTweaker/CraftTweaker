package crafttweaker.api.recipes.expand;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.recipes.ICraftingRecipe;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author youyihj
 */
@ZenRegister
@ZenExpansion("crafttweaker.recipes.ICraftingRecipe")
public class ExpandCraftingRecipe {
    @ZenMethod
    public static void remove(ICraftingRecipe recipe) {
        CraftTweakerAPI.recipes.removeByRecipeName(recipe.getFullResourceName(), null);
    }
}
