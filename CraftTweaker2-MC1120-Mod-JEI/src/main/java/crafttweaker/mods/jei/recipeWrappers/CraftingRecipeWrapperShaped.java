package crafttweaker.mods.jei.recipeWrappers;

import crafttweaker.mc1120.recipes.*;
import crafttweaker.mods.jei.JEIAddonPlugin;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class CraftingRecipeWrapperShaped implements IShapedCraftingRecipeWrapper {
    
    private final MCRecipeShaped recipe;
    
    public CraftingRecipeWrapperShaped(MCRecipeShaped recipe) {
        this.recipe = recipe;
    }
    
    public static void registerCraftingRecipes() {
        List<CraftingRecipeWrapperShaped> wrappers = MCRecipeManager.recipesToAdd.stream().map(MCRecipeManager.ActionBaseAddRecipe::getRecipe).filter(MCRecipeShaped.class::isInstance).filter(MCRecipeBase::isVisible).map(MCRecipeShaped.class::cast).map(MCRecipeShaped::update).map(CraftingRecipeWrapperShaped::new).collect(Collectors.toList());
        JEIAddonPlugin.modRegistry.addRecipes(wrappers, VanillaRecipeCategoryUid.CRAFTING);
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getIngredients().stream().map(Ingredient::getMatchingStacks).map(Arrays::asList).collect(Collectors.toList()));
    }
    
    @Override
    public int getWidth() {
        return recipe.getRecipeWidth();
    }
    
    @Override
    public int getHeight() {
        return recipe.getRecipeHeight();
    }
    
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getRegistryName();
    }
}
