package minetweaker.mods.jei;

import mezz.jei.Internal;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.*;
import minetweaker.MineTweakerAPI;
import minetweaker.api.compat.DummyJEIRecipeRegistry;


@mezz.jei.api.JEIPlugin
public class JEIAddonPlugin implements IModPlugin {
    
    public static IJeiHelpers jeiHelpers;
    public static IIngredientRegistry itemRegistry;
    public static IRecipeRegistry recipeRegistry;
    
    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
    
    }
    
    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
    
    }
    
    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        itemRegistry = registry.getIngredientRegistry();
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        recipeRegistry = iJeiRuntime.getRecipeRegistry();
        
        if(MineTweakerAPI.getIjeiRecipeRegistry() instanceof DummyJEIRecipeRegistry) {
            MineTweakerAPI.setIjeiRecipeRegistry(new JEIRecipeRegistry(recipeRegistry, jeiHelpers));
        }
    }
    
    
}
