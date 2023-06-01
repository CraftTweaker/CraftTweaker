package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.ICookingRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this smoker
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.SmokerRecipeManager")
@Document("vanilla/api/recipe/manager/SmokerRecipeManager")
public enum SmokerRecipeManager implements ICookingRecipeManager<SmokingRecipe> {
    
    @ZenCodeGlobals.Global("smoker")
    INSTANCE;
    
    @Override
    public SmokingRecipe makeRecipe(String name, CookingBookCategory category, IItemStack output, IIngredient input, float xp, int cookTime) {
        
        return new SmokingRecipe(CraftTweakerConstants.rl(name), "", category, input.asVanillaIngredient(), output.getInternal(), xp, cookTime);
    }
    
    @Override
    public RecipeType<SmokingRecipe> getRecipeType() {
        
        return RecipeType.SMOKING;
    }
    
}
