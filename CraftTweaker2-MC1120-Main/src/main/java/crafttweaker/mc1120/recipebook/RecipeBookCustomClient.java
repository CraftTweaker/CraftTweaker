package crafttweaker.mc1120.recipebook;

import net.minecraft.client.util.RecipeBookClient;

/**
 * @author BloodWorkXGaming
 *         The whole reason for this class is to make it survive checks on removed recipes
 */
public class RecipeBookCustomClient extends RecipeBookClient {


//    @Override
//    public void setRecipes(IRecipe recipe) {
//        if (!recipe.isDynamic()) {
//            int id = CraftingManager.REGISTRY.getIDForObject(recipe);
//            if (id >= 0) this.recipes.set(id);
//        }
//    }
//
//    @Override
//    public void removeRecipe(IRecipe recipe) {
//        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
//        if (id >= 0) {
//            this.recipes.clear(id);
//            this.newRecipes.clear(id);
//        }
//    }
//
//    @Override
//    public boolean containsRecipe(IRecipe recipe) {
//        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
//        return id >= 0 && this.recipes.get(id);
//    }
//
//    @Override
//    public void unlock(IRecipe recipe) {
//        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
//        if (id >= 0) this.newRecipes.clear(id);
//    }
//    
//    @Override
//    @SideOnly(Side.CLIENT)
//    public boolean isNew(IRecipe recipe) {
//        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
//        return id >= 0 && this.newRecipes.get(id);
//    }
//
//    @Override
//    public void addDisplayedRecipe(IRecipe recipe) {
//        int id = CraftingManager.REGISTRY.getIDForObject(recipe);
//        if (id >= 0) this.newRecipes.set(id);
//    }

}
