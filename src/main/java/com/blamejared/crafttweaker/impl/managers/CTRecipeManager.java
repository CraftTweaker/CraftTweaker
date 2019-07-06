package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShaped;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShapeless;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.CTRecipeManager")
public class CTRecipeManager implements IRecipeManager {
    
    @ZenCodeGlobals.Global("recipes")
    public static final CTRecipeManager INSTANCE = new CTRecipeManager();
    public static RecipeManager recipeManager;
    //private final List<ActionAddCraftingRecipe> addedRecipes = new ArrayList<>();
    
    private CTRecipeManager() {
    }
    
    @ZenCodeType.Method
    public void addShaped(String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunctionShaped recipeFunction) {
        CraftTweakerAPI.apply(new ActionAddRecipe(getRecipeType(), new CTRecipeShaped(recipeName, output, ingredients, false, recipeFunction), "shaped"));
    }
    
    @ZenCodeType.Method
    public void addShapedMirrored(String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunctionShaped recipeFunction) {
        CraftTweakerAPI.apply(new ActionAddRecipe(getRecipeType(), new CTRecipeShaped(recipeName, output, ingredients, true, recipeFunction), "mirroring shaped"));
    }
    
    @ZenCodeType.Method
    public void addShapeless(String recipeName, IItemStack output, IIngredient[] ingredients, @ZenCodeType.Optional RecipeFunctionShapeless recipeFunction) {
        CraftTweakerAPI.apply(new ActionAddRecipe(getRecipeType(), new CTRecipeShapeless(recipeName, output, ingredients, recipeFunction), "shapeless"));
    }
    
    @Override
    public IRecipeType getRecipeType() {
        return IRecipeType.CRAFTING;
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
