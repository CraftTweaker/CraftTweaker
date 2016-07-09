package minetweaker.api.recipes;

import stanhebben.zenscript.annotations.ZenClass;

/**
 * Created by Jared on 6/2/2016.
 */
@ZenClass("minetweaker.recipes.IBrewingRecipe")
public interface IBrewingRecipe {

    public String toCommandString();
}
