package crafttweaker.mods.jei.recipeWrappers;

import crafttweaker.mc1120.recipes.*;
import crafttweaker.mods.jei.JEIAddonPlugin;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.*;
import java.util.stream.Collectors;

public class CraftingRecipeWrapperShapeless implements ICraftingRecipeWrapper {
    
    private final MCRecipeShapeless recipe;
    
    public CraftingRecipeWrapperShapeless(MCRecipeShapeless recipe) {
        this.recipe = recipe;
    }
    
    public static void registerCraftingRecipes() {
        List<CraftingRecipeWrapperShapeless> wrappers = MCRecipeManager.recipesToAdd.stream().map(MCRecipeManager.ActionBaseAddRecipe::getRecipe).filter(MCRecipeBase::isVisible).filter(MCRecipeShapeless.class::isInstance).map(MCRecipeShapeless.class::cast).map(CraftingRecipeWrapperShapeless::new).collect(Collectors.toList());
        JEIAddonPlugin.modRegistry.addRecipes(wrappers, VanillaRecipeCategoryUid.CRAFTING);
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
        ingredients.setInputs(ItemStack.class, recipe.getIngredients().stream().map(Ingredient::getMatchingStacks).map(Arrays::asList).collect(Collectors.toList()));
    }
}
