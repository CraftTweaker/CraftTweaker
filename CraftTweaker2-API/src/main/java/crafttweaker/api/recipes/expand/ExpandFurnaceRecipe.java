package crafttweaker.api.recipes.expand;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.recipes.IFurnaceRecipe;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author youyihj
 */
@ZenRegister
@ZenExpansion("crafttweaker.recipes.IFurnaceRecipe")
public class ExpandFurnaceRecipe {
    @ZenMethod
    public static void remove(IFurnaceRecipe recipe) {
        CraftTweakerAPI.furnace.remove(recipe.getOutput(), recipe.getInput());
    }
}
