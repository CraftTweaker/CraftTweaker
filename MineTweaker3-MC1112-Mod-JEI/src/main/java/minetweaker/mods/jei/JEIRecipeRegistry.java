package minetweaker.mods.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.*;
import mezz.jei.ingredients.IngredientInformation;
import mezz.jei.plugins.vanilla.furnace.*;
import minetweaker.api.compat.IJEIRecipeRegistry;
import minetweaker.api.item.IIngredient;
import minetweaker.mc1112.util.MineTweakerHacks;
import net.minecraft.item.ItemStack;

import java.util.*;

public class JEIRecipeRegistry implements IJEIRecipeRegistry {

    private IRecipeRegistry recipeRegistry;
    private IJeiHelpers jeiHelpers;
    private static Map<String, List<String>> TOOLTIP_CACHE;

    public JEIRecipeRegistry(IRecipeRegistry recipeRegistry, IJeiHelpers jeiHelpers) {
        this.recipeRegistry = recipeRegistry;
        this.jeiHelpers = jeiHelpers;
    }
    
    @Override
    public void addRecipe(Object object) {
        recipeRegistry.addRecipe(object);
    }
    
    @Override
    public void addRecipe(Object object, String category) {
        if(recipeRegistry.getRecipeWrapper(object, category) != null)
            recipeRegistry.addRecipe(recipeRegistry.getRecipeWrapper(object, category), category);
    }
    
    @Override
    public void removeRecipe(Object object) {
        recipeRegistry.removeRecipe(object);
    }
    
    @Override
    public void removeRecipe(Object output, String category) {
        if(recipeRegistry.getRecipeWrapper(output, category) != null)
            recipeRegistry.removeRecipe(recipeRegistry.getRecipeWrapper(output, category), category);
    }
    
    @Override
    public void addFurnace(List<Object> input, Object output) {
        List<ItemStack> inputs = new ArrayList<>();
        input.forEach(in -> {
            inputs.add((ItemStack) in);
        });
        recipeRegistry.addRecipe(new SmeltingRecipe(inputs, (ItemStack) output), VanillaRecipeCategoryUid.SMELTING);
    }


    @Override
    public void removeFurnace(Object object) {
        List<ItemStack> stacks = JEIAddonPlugin.getSubTypes((ItemStack) object);
        for (ItemStack itemStack : stacks) {
            IFocus<ItemStack> focus = recipeRegistry.createFocus(IFocus.Mode.INPUT, itemStack);
            List<IRecipeCategory> categories = recipeRegistry.getRecipeCategories(Collections.singletonList(VanillaRecipeCategoryUid.SMELTING));
            for(IRecipeCategory category : categories) {
                if(category.getUid().equals(VanillaRecipeCategoryUid.SMELTING)) {
                    List<IRecipeWrapper> wrappers = recipeRegistry.getRecipeWrappers(category, focus);
                    for(IRecipeWrapper wrapper : wrappers) {
                        recipeRegistry.removeRecipe(wrapper, VanillaRecipeCategoryUid.SMELTING);
                    }
                }
            }
        }
    }
    
    public void addFuel(Collection<Object> input, int burnTime){
        List<ItemStack> inputs = new ArrayList<>();
        input.forEach(in -> {
            inputs.add((ItemStack) in);
        });
        recipeRegistry.addRecipe(new FuelRecipe(jeiHelpers.getGuiHelper(), inputs, burnTime), VanillaRecipeCategoryUid.FUEL);
    }
    
    public void removeFuel(Object object){
        IFocus<ItemStack> focus = recipeRegistry.createFocus(IFocus.Mode.INPUT, (ItemStack) object);
        List<IRecipeCategory> categories = recipeRegistry.getRecipeCategories(focus);
        for(IRecipeCategory category : categories) {
            if(category.getUid().equals(VanillaRecipeCategoryUid.FUEL)) {
                List<IRecipeWrapper> wrappers = recipeRegistry.getRecipeWrappers(category, focus);
                for(IRecipeWrapper wrapper : wrappers) {
                    recipeRegistry.removeRecipe(wrapper, VanillaRecipeCategoryUid.SMELTING);
                }
            }
        }
    }

    // Unused in 1.11.2
    @Override
    public void reloadItemList() {}

    @Override
    public void removeItem(Object stack) {
        JEIAddonPlugin.itemRegistry.removeIngredientsAtRuntime(ItemStack.class, Collections.singletonList((ItemStack) stack));
    }

    @Override
    public void addItem(Object stack) {
        JEIAddonPlugin.itemRegistry.addIngredientsAtRuntime(ItemStack.class, Collections.singletonList((ItemStack) stack));
    }

    @Override
    public void invalidateTooltips(IIngredient ingredient) {
        if (TOOLTIP_CACHE == null)
            TOOLTIP_CACHE = MineTweakerHacks.getPrivateStaticObject(IngredientInformation.class, "TOOLTIP_CACHE");

        if (TOOLTIP_CACHE != null) {
            TOOLTIP_CACHE.clear();
            removeItem(ingredient.getInternal());
            addItem(ingredient.getInternal());
        }
    }
}
