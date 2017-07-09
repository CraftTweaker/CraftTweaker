package crafttweaker.mc1120.recipebook;

import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;

/**
 * Created by jonas on 09.07.2017.
 */
public class RecipeBookCustom extends RecipeBookClient {

    @Override
    public boolean containsRecipe(IRecipe recipe)
    {
        int id = getRecipeId(recipe);
        return id >= 0 && this.recipes.get(id);
    }
}
