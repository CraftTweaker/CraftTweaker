package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.func.RecipeFunction1D;
import com.blamejared.crafttweaker.api.recipe.func.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @docParam this craftingTable
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.CraftingTableRecipeManager")
@Document("vanilla/api/recipe/manager/CraftingTableRecipeManager")
public enum CraftingTableRecipeManager implements IRecipeManager<CraftingRecipe> {
    
    @ZenCodeGlobals.Global("craftingTable")
    INSTANCE;
    
    
    @ZenCodeType.Method
    public void addShaped(String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunction2D recipeFunction) {
        
        recipeName = fixRecipeName(recipeName);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, Services.REGISTRY.createCTShapedRecipe(recipeName, output, ingredients, MirrorAxis.NONE, recipeFunction), "shaped"));
    }
    
    @ZenCodeType.Method
    public void addShapedPattern(String recipeName, IItemStack output, String[] pattern, Map<String, IIngredient> keys, @ZenCodeType.Optional RecipeFunction2D recipeFunction) {
        
        // TODO right now this requires casting the map nicely, which is not ideal at all, we need to add some rewrites for it
        recipeName = fixRecipeName(recipeName);
        
        int height = pattern.length;
        int width = Arrays.stream(pattern).mapToInt(String::length).max().orElse(0);
        
        IIngredient[][] ingredients = dissolvePattern(pattern, keys, width, height);
        
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, Services.REGISTRY.createCTShapedRecipe(recipeName, output, ingredients, MirrorAxis.NONE, recipeFunction), "shaped"));
    }
    
    /**
     * Adds a mirrored shaped recipe to the crafting table.
     *
     * This method lets you provide a {@link MirrorAxis}, which can be used to set which axis the recipe is mirrored on.
     * Use cases are making a recipe only be mirrored vertically or only horizontally.
     *
     * @param recipeName     name of the recipe to add.
     * @param mirrorAxis     The axis that this recipe mirrored on.
     * @param output         output {@link IItemStack}
     * @param ingredients    array of an array of {@link IIngredient} for inputs
     * @param recipeFunction optional {@link RecipeFunction2D} for more advanced conditions
     *
     * @docParam recipeName "recipe_name"
     * @docParam mirrorAxis MirrorAxis.DIAGONAL
     * @docParam output <item:minecraft:dirt>
     * @docParam ingredients [[<item:minecraft:diamond>], [<tag:items:minecraft:wool>]]
     * @docParam recipeFunction (usualOut as IItemStack, inputs as IItemStack[][]) => {if(inputs[0][0].displayName == "totally real diamond block" ){return usualOut;}return <item:minecraft:clay>.setDisplayName("Diamond");}
     */
    @ZenCodeType.Method
    public void addShapedMirrored(String recipeName, MirrorAxis mirrorAxis, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunction2D recipeFunction) {
        
        recipeName = fixRecipeName(recipeName);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, Services.REGISTRY.createCTShapedRecipe(recipeName, output, ingredients, mirrorAxis, recipeFunction), "mirroring shaped"));
    }
    
    @ZenCodeType.Method
    public void addShapeless(String recipeName, IItemStack output, IIngredient[] ingredients, @ZenCodeType.Optional RecipeFunction1D recipeFunction) {
        
        recipeName = fixRecipeName(recipeName);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, Services.REGISTRY.createCTShapelessRecipe(recipeName, output, ingredients, recipeFunction), "shapeless"));
    }
    
    @Override
    public RecipeType<CraftingRecipe> getRecipeType() {
        
        return RecipeType.CRAFTING;
    }
    
    
    public static IIngredient[][] dissolvePattern(String[] pattern, Map<String, IIngredient> keys, int width, int height) {
        
        // " " is reserved for empty
        keys.put(" ", IIngredientEmpty.INSTANCE);
        IIngredient[][] ingredients = new IIngredient[height][width];
        Set<String> set = Sets.newHashSet(keys.keySet());
        set.remove(" ");
        
        
        for(int row = 0; row < pattern.length; ++row) {
            for(int col = 0; col < pattern[row].length(); ++col) {
                String s = pattern[row].substring(col, col + 1);
                IIngredient ingredient = keys.get(s);
                if(ingredient == null) {
                    throw new IllegalArgumentException("Pattern references symbol '" + s + "' but it is not defined in the key");
                }
                
                set.remove(s);
                ingredients[row][col] = ingredient;
            }
        }
        
        if(!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return ingredients;
        }
    }
}
