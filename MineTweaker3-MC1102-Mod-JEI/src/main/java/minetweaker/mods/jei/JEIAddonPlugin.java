package minetweaker.mods.jei;

import mezz.jei.api.*;


@mezz.jei.api.JEIPlugin
public class JEIAddonPlugin implements IModPlugin {
    public static IJeiHelpers jeiHelpers;
    public static IItemRegistry itemRegistry;
    public static IRecipeRegistry recipeRegistry;


    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        itemRegistry = registry.getItemRegistry();

        // The blacklist items must be registered here, otherwise the item filters are already created and
        // anything that is added doesn't have an effect
        JEI.onJEIStarted();
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        recipeRegistry = iJeiRuntime.getRecipeRegistry();
    }


}
