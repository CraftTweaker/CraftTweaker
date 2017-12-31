package crafttweaker.mc1120.recipebook;

import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author BloodWorkXGaming
 * The whole reason for this class is to make it survive checks on removed recipes
 */
public class RecipeBookCustomClient extends RecipeBookClient {


    @Override
    public void setRecipes(IRecipe recipe) {
        if (!recipe.isHidden()) {
            int id = CraftingManager.REGISTRY.getIDForObject(recipe);
            if (id >= 0) this.recipes.set(id);
        }
    }

    @Override
    public void removeRecipe(IRecipe recipe) {
        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
        if (id >= 0) {
            this.recipes.clear(id);
            this.unseenRecipes.clear(id);
        }
    }

    @Override
    public boolean containsRecipe(IRecipe recipe) {
        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
        return id >= 0 && this.recipes.get(id);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isRecipeUnseen(IRecipe recipe) {
        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
        return id >= 0 && this.unseenRecipes.get(id);
    }

    @Override
    public void setRecipeSeen(IRecipe recipe) {
        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
        if (id >= 0) this.unseenRecipes.clear(id);
    }

    @Override
    public void addDisplayedRecipe(IRecipe recipe) {
        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
        if (id >= 0) this.unseenRecipes.set(id);
    }

}
