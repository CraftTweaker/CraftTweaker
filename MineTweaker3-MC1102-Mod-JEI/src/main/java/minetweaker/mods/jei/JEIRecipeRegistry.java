package minetweaker.mods.jei;

import mezz.jei.api.IRecipeRegistry;
import minetweaker.api.compat.IJEIRecipeRegistry;

public class JEIRecipeRegistry implements IJEIRecipeRegistry{
private IRecipeRegistry recipeRegistry;
	
	public JEIRecipeRegistry(IRecipeRegistry recipeRegistry) {
		this.recipeRegistry = recipeRegistry;
	}
	
	@Override
	public void addRecipe(Object object) {
		recipeRegistry.addRecipe(object);
	}
	
	@Override
	public void removeRecipe(Object object) {
		recipeRegistry.removeRecipe(object);
	}
}
