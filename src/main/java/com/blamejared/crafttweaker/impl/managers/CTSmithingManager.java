package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this smithing
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.SmithingManager")
@Document("vanilla/api/managers/SmithingManager")
public class CTSmithingManager implements IRecipeManager {
    
    @ZenCodeGlobals.Global("smithing")
    public static final CTSmithingManager INSTANCE = new CTSmithingManager();
    
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
    @ZenCodeType.Method
    public void addRecipe(String recipeName, IItemStack result, IIngredient base, IIngredient addition) {
        
        recipeName = validateRecipeName(recipeName);
        final SmithingRecipe smithing = new SmithingRecipe(new ResourceLocation(CraftTweaker.MODID, recipeName), base.asVanillaIngredient(), addition.asVanillaIngredient(), result.getInternal());
        CraftTweakerAPI.apply(new ActionAddRecipe(this, smithing, ""));
    }
    
    @Override
    public IRecipeType<SmithingRecipe> getRecipeType() {
        
        return IRecipeType.SMITHING;
    }
    
}