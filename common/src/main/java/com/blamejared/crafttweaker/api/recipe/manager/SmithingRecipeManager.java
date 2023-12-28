package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessSmithingTrimRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this smithing
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.SmithingRecipeManager")
@Document("vanilla/api/recipe/manager/SmithingRecipeManager")
public class SmithingRecipeManager implements IRecipeManager<SmithingRecipe> {
    
    @ZenCodeGlobals.Global("smithing")
    public static final SmithingRecipeManager INSTANCE = new SmithingRecipeManager();
    
    private SmithingRecipeManager() {}
    
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
        
        final SmithingRecipe recipe = AccessSmithingTrimRecipe.crafttweaker$createSmithingTrimRecipe(template.asVanillaIngredient(), base.asVanillaIngredient(), addition.asVanillaIngredient());
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(fixRecipeId(recipeName), recipe), "trim"));
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
        
        final SmithingRecipe recipe = new SmithingTransformRecipe(template.asVanillaIngredient(), base.asVanillaIngredient(), addition.asVanillaIngredient(), result.getInternal());
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(fixRecipeId(recipeName), recipe), "transform"));
    }
    
    @Override
    public RecipeType<SmithingRecipe> getRecipeType() {
        
        return RecipeType.SMITHING;
    }
    
}
