package crafttweaker.mods.jei.recipeWrappers;

import crafttweaker.mc1120.recipes.*;
import crafttweaker.mods.jei.JEIAddonPlugin;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.*;
import java.util.stream.Collectors;

public class CraftingRecipeWrapperShaped implements IShapedCraftingRecipeWrapper {
    
    private final MCRecipeShaped recipe;
    
    public CraftingRecipeWrapperShaped(MCRecipeShaped recipe) {
        this.recipe = recipe;
    }
    
    public static void registerCraftingRecipes() {
        List<CraftingRecipeWrapperShaped> wrappers = MCRecipeManager.recipesToAdd.stream().map(MCRecipeManager.ActionBaseAddRecipe::getRecipe).filter(MCRecipeBase::isVisible).filter(MCRecipeShaped.class::isInstance).map(MCRecipeShaped.class::cast).map(CraftingRecipeWrapperShaped::new).collect(Collectors.toList());
        JEIAddonPlugin.modRegistry.addRecipes(wrappers, VanillaRecipeCategoryUid.CRAFTING);
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
        ingredients.setInputs(ItemStack.class, recipe.getIngredients().stream().map(Ingredient::getMatchingStacks).map(Arrays::asList).collect(Collectors.toList()));
    }
    
    @Override
    public int getWidth() {
        return recipe.getRecipeWidth();
    }
    
    @Override
    public int getHeight() {
        return recipe.getRecipeHeight();
    }
}
