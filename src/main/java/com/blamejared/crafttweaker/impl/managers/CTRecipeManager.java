package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.*;
import com.blamejared.crafttweaker.impl.actions.recipes.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.CTRecipeManager")
public class CTRecipeManager implements IRegistryManager {
    
    @ZenCodeGlobals.Global("recipes")
    public static final CTRecipeManager INSTANCE = new CTRecipeManager();
    public static RecipeManager recipeManager;
    
    private CTRecipeManager() {
    }
    
    @ZenCodeType.Method
    public void addShaped(String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Nullable @ZenCodeType.Optional RecipeFunctionShaped recipeFunction) {
        //addedRecipes.add(new ActionAddCraftingRecipe.Shaped(recipeName, output, ingredients, true, recipeFunction));
    }
    
    @ZenCodeType.Method
    public void addShapeless(String recipeName, IItemStack output, IIngredient[] ingredients, @ZenCodeType.Nullable @ZenCodeType.Optional RecipeFunctionShapeless recipeFunction) {
        //addedRecipes.add(new ActionAddCraftingRecipe.Shapeless(recipeName, output, ingredients, recipeFunction));
    }
    
    @ZenCodeType.Method
    public void removeRecipe(IIngredient output) {
        //ActionRemoveRecipeNoIngredients.INSTANCE.addOutput(output);
        CraftTweakerAPI.apply(new ActionRemoveRecipeByOutput(IRecipeType.CRAFTING, output));
    }
    
    @Override
    public void removeByName(String name) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByName(IRecipeType.CRAFTING, new ResourceLocation(name)));
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
