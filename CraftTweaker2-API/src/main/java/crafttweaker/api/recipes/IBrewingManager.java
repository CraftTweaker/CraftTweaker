package crafttweaker.api.recipes;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenMethod;

public interface IBrewingManager {
	
	@ZenMethod
	void addBrew(IItemStack input, IItemStack ingredient, IItemStack output, @Optional boolean useInputNBT);
	
	@ZenMethod
	void addBrew(IItemStack input, IOreDictEntry ingredient, IItemStack output, @Optional boolean useInputNBT);
	
	@ZenMethod
	void addBrew(IItemStack input, IIngredient ingredient, IItemStack output, @Optional boolean useInputNBT);

	@ZenMethod
	void addBrew(IItemStack input, IItemStack[] ingredients, IItemStack output, @Optional boolean useInputNBT);
	
	@ZenMethod
	void addHiddenBrew(IIngredient input, IIngredient ingredient, IItemStack output);

}
