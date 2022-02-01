package com.blamejared.crafttweaker.api.recipe.manager;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.ICookingRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this campfire
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.CampFireRecipeManager")
@Document("vanilla/api/recipe/manager/CampFireRecipeManager")
public enum CampFireRecipeManager implements ICookingRecipeManager<CampfireCookingRecipe> {
    
    @ZenCodeGlobals.Global("campfire")
    INSTANCE;
    
    @Override
    public CampfireCookingRecipe makeRecipe(String name, IItemStack output, IIngredient input, float xp, int cookTime) {
        
        return new CampfireCookingRecipe(CraftTweakerConstants.rl(name), "", input.asVanillaIngredient(), output.getInternal(), xp, cookTime);
    }
    
    @Override
    public RecipeType<CampfireCookingRecipe> getRecipeType() {
        
        return RecipeType.CAMPFIRE_COOKING;
    }
    
}
