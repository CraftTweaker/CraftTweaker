package com.blamejared.crafttweaker.api.recipe;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.brewing.ActionAddBrewingRecipe;
import com.blamejared.crafttweaker.api.action.brewing.ActionRemoveBrewingRecipe;
import com.blamejared.crafttweaker.api.action.brewing.ActionRemoveBrewingRecipeByInput;
import com.blamejared.crafttweaker.api.action.brewing.ActionRemoveBrewingRecipeByPotionInput;
import com.blamejared.crafttweaker.api.action.brewing.ActionRemoveBrewingRecipeByPotionOutput;
import com.blamejared.crafttweaker.api.action.brewing.ActionRemoveBrewingRecipeByReagent;
import com.blamejared.crafttweaker.api.action.brewing.ActionRemovePotionBrewingRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.mixin.common.access.brewing.AccessBrewingRecipeRegistry;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.brewing.BrewingRecipe;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this brewing
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.Brewing")
@Document("forge/api/recipe/brewing/Brewing")
public final class CTBrewing {
    
    @ZenCodeGlobals.Global("brewing")
    public static final CTBrewing INSTANCE = new CTBrewing();
    
    private CTBrewing() {
        
    }
    
    /**
     * Adds a new brewing recipe to the Brewing Stand.
     *
     * @param output  The item that the recipe outputs.
     * @param reagent The reagent that is put in the top slot of the Brewing Stand.
     * @param input   The Ingredient that get brewed into the output. E.G. a Water bottle getting brewed into a Thick Potion.
     *
     * @docParam output <item:minecraft:dirt>
     * @docParam reagent <item:minecraft:apple>
     * @docParam input <item:minecraft:arrow>
     */
    @ZenCodeType.Method
    public void addRecipe(IItemStack output, IIngredient reagent, IIngredient input) {
        
        BrewingRecipe recipe = new BrewingRecipe(input.asVanillaIngredient(), reagent.asVanillaIngredient(), output
                .getInternal());
        CraftTweakerAPI.apply(new ActionAddBrewingRecipe(AccessBrewingRecipeRegistry.crafttweaker$getRecipes(), recipe));
    }
    
    /**
     * Removes a Potion to Potion recipe from the Brewing Stand. These are mainly the default vanilla recipes.
     *
     * @param output  The Potion that the recipe outputs.
     * @param reagent The reagent that is put in the top slot of the Brewing Stand.
     * @param input   The Potion ingredient that get brewed into the output. E.G. a Water bottle getting brewed into a Thick Potion.
     *
     * @docParam output <potion:minecraft:thick>
     * @docParam reagent <item:minecraft:glowstone_dust>
     * @docParam input <potion:minecraft:water>
     */
    @ZenCodeType.Method
    public void removeRecipe(Potion output, IItemStack reagent, Potion input) {
        
        CraftTweakerAPI.apply(new ActionRemovePotionBrewingRecipe(AccessBrewingRecipeRegistry.crafttweaker$getRecipes(), output, reagent, input));
    }
    
    /**
     * Removes an ItemStack to ItemStack recipe from the Brewing Stand. These are mainly potions added by mods.
     *
     * @param output  The ItemStack that the recipe outputs.
     * @param reagent The reagent that is put in the top slot of the Brewing Stand.
     * @param input   The Ingredient that get brewed into the output. E.G. a Water bottle getting brewed into a Thick Potion.
     *
     * @docParam output <item:minecraft:glass>
     * @docParam reagent <item:minecraft:diamond>
     * @docParam input <item:minecraft:stick>
     */
    @ZenCodeType.Method
    public void removeRecipe(IItemStack output, IItemStack reagent, IItemStack input) {
        
        CraftTweakerAPI.apply(new ActionRemoveBrewingRecipe(AccessBrewingRecipeRegistry.crafttweaker$getRecipes(), output, reagent, input));
    }
    
    /**
     * Removes recipes from the Brewing Stand based on their Reagent (The item in the top slot).
     *
     * @param reagent The reagent of the recipes to remove.
     *
     * @docParam reagent <item:minecraft:golden_carrot>
     */
    @ZenCodeType.Method
    public void removeRecipeByReagent(IItemStack reagent) {
        
        CraftTweakerAPI.apply(new ActionRemoveBrewingRecipeByReagent(AccessBrewingRecipeRegistry.crafttweaker$getRecipes(), reagent));
    }
    
    /**
     * Removes recipes from the Brewing Stand based on their Input (The ItemStack that goes in the bottom 3 slots). E.G. A water bottle in Vanilla brewing recipes
     *
     * @param input The input of the recipes to remove.
     *
     * @docParam input <item:minecraft:glass>
     */
    @ZenCodeType.Method
    public void removeRecipeByInput(IItemStack input) {
        
        CraftTweakerAPI.apply(new ActionRemoveBrewingRecipeByInput(AccessBrewingRecipeRegistry.crafttweaker$getRecipes(), input));
    }
    
    /**
     * Removes recipes from the Brewing Stand based on their output Potion. These are mainly the default vanilla recipes.
     *
     * @param output The potion of the recipes to remove.
     *
     * @docParam output <potion:minecraft:swiftness>
     */
    @ZenCodeType.Method
    public void removeRecipeByOutputPotion(Potion output) {
        
        CraftTweakerAPI.apply(new ActionRemoveBrewingRecipeByPotionOutput(AccessBrewingRecipeRegistry.crafttweaker$getRecipes(), output));
    }
    
    /**
     * Removes recipes from the Brewing Stand based on their input Potion. These are mainly the default vanilla recipes.
     * The input potion is the potion that is in the top slot of the Brewing Stand.
     *
     * @param input The input potion of the recipes to remove.
     *
     * @docParam output <potion:minecraft:awkward>
     */
    @ZenCodeType.Method
    public void removeRecipeByInputPotion(Potion input) {
        
        CraftTweakerAPI.apply(new ActionRemoveBrewingRecipeByPotionInput(AccessBrewingRecipeRegistry.crafttweaker$getRecipes(), input));
    }
    
}
