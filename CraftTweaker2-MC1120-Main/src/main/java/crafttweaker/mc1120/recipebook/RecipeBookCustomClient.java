package crafttweaker.mc1120.recipebook;

import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.item.crafting.IRecipe;

/**
 * @author BloodWorkXGaming
 */
public class RecipeBookCustomClient extends RecipeBookClient {

    @Override
    public boolean containsRecipe(IRecipe recipe)
    {
        int id = getRecipeId(recipe);
        return id >= 0 && this.recipes.get(id);
    }
}
