package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShaped;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShapeless;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this craftingTable
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.CraftingTableManager")
@Document("vanilla/managers/CraftingTableManager")
public class CTCraftingTableManager implements IRecipeManager {
    
    @ZenCodeGlobals.Global("craftingTable")
    public static final CTCraftingTableManager INSTANCE = new CTCraftingTableManager();
    public static RecipeManager recipeManager;
    //private final List<ActionAddCraftingRecipe> addedRecipes = new ArrayList<>();
    
    private CTCraftingTableManager() {
    }
    /**
     * Adds a shaped recipe to the crafting table
     *
     * @param recipeName     name of the recipe to add.
     * @param output         output {@link IItemStack}
     * @param ingredients    array of an array of {@link IIngredient} for inputs
     * @param recipeFunction optional {@link com.blamejared.crafttweaker.api.managers.IRecipeManager.RecipeFunctionMatrix} for more advanced conditions
     *
     * @docParam recipeName "recipe_name"
     * @docParam output <item:minecraft:dirt>
     * @docParam ingredients [[<item:minecraft:diamond>], [<tag:minecraft:wool>]]
     * @docParam recipeFunction (usualOut as IItemStack, inputs as IItemStack[][]) => {if(inputs[0][0].displayName == "totally real diamond block" ){return usualOut;}return <item:minecraft:clay>.setDisplayName("Diamond");}
     */
    @ZenCodeType.Method
    public void addShaped(String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunctionMatrix recipeFunction) {
        CraftTweakerAPI.apply(new ActionAddRecipe(this, new CTRecipeShaped(recipeName, output, ingredients, false, recipeFunction), "shaped"));
    }
    
    /**
     * Adds a mirrored shaped recipe to the crafting table
     *
     * @param recipeName     name of the recipe to add.
     * @param output         output {@link IItemStack}
     * @param ingredients    array of an array of {@link IIngredient} for inputs
     * @param recipeFunction optional {@link com.blamejared.crafttweaker.api.managers.IRecipeManager.RecipeFunctionMatrix} for more advanced conditions
     *
     * @docParam recipeName "recipe_name"
     * @docParam output <item:minecraft:dirt>
     * @docParam ingredients [[<item:minecraft:diamond>], [<tag:minecraft:wool>]]
     * @docParam recipeFunction (usualOut as IItemStack, inputs as IItemStack[][]) => {if(inputs[0][0].displayName == "totally real diamond block" ){return usualOut;}return <item:minecraft:clay>.setDisplayName("Diamond");}
     */
    @ZenCodeType.Method
    public void addShapedMirrored(String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunctionMatrix recipeFunction) {
        CraftTweakerAPI.apply(new ActionAddRecipe(this, new CTRecipeShaped(recipeName, output, ingredients, true, recipeFunction), "mirroring shaped"));
    }
    
    /**
     * Adds a shapeless recipe to the crafting table
     *
     * @param recipeName     name of the recipe to add.
     * @param output         output {@link IItemStack}
     * @param ingredients    array of {@link IIngredient} for inputs
     * @param recipeFunction optional {@link com.blamejared.crafttweaker.api.managers.IRecipeManager.RecipeFunctionArray} for more advanced conditions
     *
     * @docParam recipeName "recipe_name"
     * @docParam output <item:minecraft:dirt>
     * @docParam ingredients [<item:minecraft:diamond>, <tag:minecraft:wool>]
     * @docParam recipeFunction (usualOut as IItemStack, inputs as IItemStack[]) => {if(inputs[0].displayName == "totally real diamond block" ){return usualOut;}return <item:minecraft:clay>.setDisplayName("Diamond");}
     */
    @ZenCodeType.Method
    public void addShapeless(String recipeName, IItemStack output, IIngredient[] ingredients, @ZenCodeType.Optional RecipeFunctionArray recipeFunction) {
        CraftTweakerAPI.apply(new ActionAddRecipe(this, new CTRecipeShapeless(recipeName, output, ingredients, recipeFunction), "shapeless"));
    }
    
    @Override
    public IRecipeType getRecipeType() {
        return IRecipeType.CRAFTING;
    }
    
    //public List<ActionAddCraftingRecipe> getAddedRecipes() {
    //    return addedRecipes;
    //}
    
    
}
