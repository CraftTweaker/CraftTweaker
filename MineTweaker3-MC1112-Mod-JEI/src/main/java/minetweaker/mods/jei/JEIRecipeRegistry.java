package minetweaker.mods.jei;

import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.*;
import minetweaker.api.compat.IJEIRecipeRegistry;
import net.minecraft.item.ItemStack;

import java.util.*;

public class JEIRecipeRegistry implements IJEIRecipeRegistry {

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

    @Override
    public void addFurnace(List<Object> input, Object output) {
        List<ItemStack> inputs = new ArrayList<>();
        input.forEach(in -> {
            inputs.add((ItemStack) in);
        });
        		recipeRegistry.addSmeltingRecipe(inputs, (ItemStack) output);
    }


    @Override
    public void removeFurnace(Object object) {
        IFocus<ItemStack> focus = recipeRegistry.createFocus(IFocus.Mode.INPUT, (ItemStack) object);
        List<IRecipeCategory> categories = recipeRegistry.getRecipeCategories(focus);
        for(IRecipeCategory category : categories) {
            if(category.getUid().equals(VanillaRecipeCategoryUid.SMELTING)) {
                List<IRecipeWrapper> wrappers = recipeRegistry.getRecipeWrappers(category, focus);
                for(IRecipeWrapper wrapper : wrappers) {
                    recipeRegistry.removeRecipe(wrapper);
                }
            }
        }
    }
}
