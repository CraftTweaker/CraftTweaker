package minetweaker.mods.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import minetweaker.MineTweakerAPI;
import minetweaker.api.compat.DummyJEIRecipeRegistry;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;


@mezz.jei.api.JEIPlugin
public class JEIAddonPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelpers;
    public static IIngredientRegistry itemRegistry;
    public static IRecipeRegistry recipeRegistry;
    public static IIngredientHelper<ItemStack> ingredientHelper;

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
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        recipeRegistry = iJeiRuntime.getRecipeRegistry();

        if(MineTweakerAPI.getIjeiRecipeRegistry() instanceof DummyJEIRecipeRegistry) {
            MineTweakerAPI.setIjeiRecipeRegistry(new JEIRecipeRegistry(recipeRegistry, jeiHelpers));
        }
    }

    public static List<ItemStack> getSubTypes(ItemStack stack) {
        return JEIAddonPlugin.ingredientHelper.expandSubtypes(Collections.singletonList(stack));
    }
}
