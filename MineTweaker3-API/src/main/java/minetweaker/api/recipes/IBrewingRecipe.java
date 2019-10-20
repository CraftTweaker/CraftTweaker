package minetweaker.api.recipes;

import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("minetweaker.recipes.IBrewingRecipe")
public interface IBrewingRecipe {
    
    String toCommandString();
}
