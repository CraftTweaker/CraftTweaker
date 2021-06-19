package crafttweaker.api.recipes;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenMethod;

public interface IBrewingManager {
	
	@ZenMethod
	void addBrew(IIngredient input, IIngredient ingredient, IItemStack output, @Optional boolean hidden);

	@ZenMethod
	void addBrew(IIngredient input, IIngredient[] ingredients, IItemStack output, @Optional boolean hidden);
	
	@ZenMethod
	void removeRecipe(IItemStack input, IItemStack ingredient);

	@ZenMethod
	void removeRecipe(IItemStack ingredient);
}
