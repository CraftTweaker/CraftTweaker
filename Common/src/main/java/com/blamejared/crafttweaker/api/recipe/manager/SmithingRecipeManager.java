package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.*;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this smithing
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.SmithingRecipeManager")
@Document("vanilla/api/recipe/manager/SmithingRecipeManager")
public enum SmithingRecipeManager implements IRecipeManager<SmithingRecipe> {
    
    @ZenCodeGlobals.Global("smithing")
    INSTANCE;
    
    
    /**
     * Adds a recipe to the smithing table.
     *
     * @param recipeName Name of the recipe.
     * @param result     The item created by the recipe.
     * @param base       The initial ingredient for the recipe.
     * @param addition   The item added to the base item.
     *
     * @docParam recipeName "recipe_name"
     * @docParam result <item:minecraft:golden_apple>
     * @docParam base <item:minecraft:apple>
     * @docParam addition <tag:items:forge:ingots/gold>
     */
    @SuppressWarnings("removal")
    @ZenCodeType.Method
    public void addLegacyRecipe(String recipeName, IItemStack result, IIngredient base, IIngredient addition) {
        
        recipeName = fixRecipeName(recipeName);
        final SmithingRecipe smithing = new LegacyUpgradeRecipe(CraftTweakerConstants.rl(recipeName), base.asVanillaIngredient(), addition.asVanillaIngredient(), result.getInternal());
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, smithing, ""));
    }
    
    /**
     * Adds a new trim recipe to the smithing table.
     *
     * @param recipeName Name of the recipe.
     * @param template   The template to use.
     * @param base       The initial ingredient for the recipe.
     * @param addition   The item added to the base item.
     *
     * @docParam recipeName "recipe_name"
     * @docParam result <item:minecraft:golden_apple>
     * @docParam base <item:minecraft:apple>
     * @docParam addition <tag:items:forge:ingots/gold>
     */
    @ZenCodeType.Method
    public void addTrimRecipe(String recipeName, IIngredient template, IIngredient base, IIngredient addition) {
        
        recipeName = fixRecipeName(recipeName);
        final SmithingRecipe smithing = new SmithingTrimRecipe(CraftTweakerConstants.rl(recipeName), template.asVanillaIngredient(), base.asVanillaIngredient(), addition.asVanillaIngredient());
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, smithing, ""));
    }
    
    /**
     * Adds a new transform recipe to the smithing table.
     *
     * @param recipeName Name of the recipe.
     * @param result     The item created by the recipe.
     * @param template   The template to use.
     * @param base       The initial ingredient for the recipe.
     * @param addition   The item added to the base item.
     *
     * @docParam recipeName "recipe_name"
     * @docParam result <item:minecraft:golden_apple>
     * @docParam base <item:minecraft:apple>
     * @docParam addition <tag:items:forge:ingots/gold>
     */
    @ZenCodeType.Method
    public void addTransformRecipe(String recipeName, IItemStack result, IIngredient template, IIngredient base, IIngredient addition) {
        
        recipeName = fixRecipeName(recipeName);
        final SmithingRecipe smithing = new SmithingTransformRecipe(CraftTweakerConstants.rl(recipeName), template.asVanillaIngredient(), base.asVanillaIngredient(), addition.asVanillaIngredient(), result.getInternal());
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, smithing, ""));
    }
    
    @Override
    public RecipeType<SmithingRecipe> getRecipeType() {
        
        return RecipeType.SMITHING;
    }
    
}
