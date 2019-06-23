package com.blamejared.crafttweaker.api.handlers.recipes.craftingtable;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.*;
import net.minecraft.item.crafting.RecipeManager;
import org.openzen.zencode.java.*;

import java.util.ArrayList;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IIngredient")
public class CrTRecipeManager {
    
    @ZenCodeGlobals.Global("recipes")
    public static final CrTRecipeManager INSTANCE = new CrTRecipeManager();
    public static RecipeManager vanillaRecipeManager = null;
    //private final List<ActionAddCraftingRecipe> addedRecipes = new ArrayList<>();
    
    private CrTRecipeManager() {
    }
    
    @ZenCodeType.Method
    public void addShaped(String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Nullable RecipeFunctionShaped recipeFunction) {
        //addedRecipes.add(new ActionAddCraftingRecipe.Shaped(recipeName, output, ingredients, true, recipeFunction));
    }
    
    @ZenCodeType.Method
    public void addShapeless(String recipeName, IItemStack output, IIngredient[] ingredients, @ZenCodeType.Nullable RecipeFunctionShapeless recipeFunction) {
        //addedRecipes.add(new ActionAddCraftingRecipe.Shapeless(recipeName, output, ingredients, recipeFunction));
    }
    
    @ZenCodeType.Method
    public void removeRecipe(IIngredient output) {
        //ActionRemoveRecipeNoIngredients.INSTANCE.addOutput(output);
    }
    
    //public List<ActionAddCraftingRecipe> getAddedRecipes() {
    //    return addedRecipes;
    //}
    
    
    @FunctionalInterface
    @ZenRegister
    public interface RecipeFunctionShaped {
        
        IItemStack process(IItemStack usualOut, IItemStack[][] inputs);
    }
    
    @FunctionalInterface
    @ZenRegister
    public interface RecipeFunctionShapeless {
        
        IItemStack process(IItemStack usualOut, IItemStack[] inputs);
    }
}
