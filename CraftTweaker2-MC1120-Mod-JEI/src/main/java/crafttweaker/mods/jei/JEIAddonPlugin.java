package crafttweaker.mods.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;


@mezz.jei.api.JEIPlugin
public class JEIAddonPlugin implements IModPlugin {
    
    public static IJeiHelpers jeiHelpers;
    public static IIngredientRegistry itemRegistry;
    public static IRecipeRegistry recipeRegistry;
    public static IIngredientHelper<ItemStack> ingredientHelper;
    public static IModRegistry modRegistry;
    public static IJeiRuntime jeiRuntime;
    
    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
    
    }
    
    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
    
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
    
    }
    
    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        itemRegistry = registry.getIngredientRegistry();
        ingredientHelper = itemRegistry.getIngredientHelper(ItemStack.class);
        modRegistry = registry;
        
        JEIMod.onRegistered();
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        recipeRegistry = iJeiRuntime.getRecipeRegistry();
        jeiRuntime = iJeiRuntime;
    }
    
    public static List<ItemStack> getSubTypes(ItemStack stack) {
        return JEIAddonPlugin.ingredientHelper.expandSubtypes(Collections.singletonList(stack));
    }
}
