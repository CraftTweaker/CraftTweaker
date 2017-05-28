package minetweaker.mods.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.*;
import mezz.jei.plugins.vanilla.furnace.*;
import minetweaker.api.compat.IJEIRecipeRegistry;
import net.minecraft.item.ItemStack;

import java.util.*;

public class JEIRecipeRegistry implements IJEIRecipeRegistry {

    private IRecipeRegistry recipeRegistry;
    private IJeiHelpers jeiHelpers;
    
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
        recipeRegistry.addRecipe(recipeRegistry.getRecipeWrapper(object, category), category);
    }
    
    @Override
    public void removeRecipe(Object object) {
        recipeRegistry.removeRecipe(object);
    }
    
    @Override
    public void removeRecipe(Object output, String category) {
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
        IFocus<ItemStack> focus = recipeRegistry.createFocus(IFocus.Mode.INPUT, (ItemStack) object);
        List<IRecipeCategory> categories = recipeRegistry.getRecipeCategories(focus);
        for(IRecipeCategory category : categories) {
            if(category.getUid().equals(VanillaRecipeCategoryUid.SMELTING)) {
                List<IRecipeWrapper> wrappers = recipeRegistry.getRecipeWrappers(category, focus);
                for(IRecipeWrapper wrapper : wrappers) {
                    recipeRegistry.removeRecipe(wrapper, VanillaRecipeCategoryUid.SMELTING);
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
}
