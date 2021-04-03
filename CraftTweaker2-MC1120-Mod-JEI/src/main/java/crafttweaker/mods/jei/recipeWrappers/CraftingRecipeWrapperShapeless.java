package crafttweaker.mods.jei.recipeWrappers;

import crafttweaker.mc1120.recipes.*;
import crafttweaker.mods.jei.JEIAddonPlugin;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class CraftingRecipeWrapperShapeless implements ICraftingRecipeWrapper {
    
    private final MCRecipeShapeless recipe;
    
    public CraftingRecipeWrapperShapeless(MCRecipeShapeless recipe) {
        this.recipe = recipe;
    }
    
    public static void registerCraftingRecipes() {
        List<CraftingRecipeWrapperShapeless> wrappers = MCRecipeManager.recipesToAdd.stream().map(MCRecipeManager.ActionBaseAddRecipe::getRecipe).filter(MCRecipeShapeless.class::isInstance).filter(MCRecipeBase::isVisible).map(MCRecipeShapeless.class::cast).map(MCRecipeShapeless::update).map(CraftingRecipeWrapperShapeless::new).collect(Collectors.toList());
        JEIAddonPlugin.modRegistry.addRecipes(wrappers, VanillaRecipeCategoryUid.CRAFTING);
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getIngredients().stream().map(Ingredient::getMatchingStacks).map(Arrays::asList).collect(Collectors.toList()));
    }
    
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getRegistryName();
    }
}
