package minetweaker.mods.jei;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;

@mezz.jei.api.JEIPlugin
public class JEIAddonPlugin implements IModPlugin{

	public static IJeiHelpers jeiHelpers;
	public static IItemRegistry itemRegistry;
	public static IRecipeRegistry recipeRegistry;
	
	@Override
	public boolean isModLoaded() {
		return true;
	}

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
		this.jeiHelpers = jeiHelpers;
	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry) {
		this.itemRegistry = itemRegistry;
	}

	@Override
	public void register(IModRegistry registry) {
	}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {
		this.recipeRegistry = recipeRegistry;
	}

}
