package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.ICookingRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this blastFurnace
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.BlastFurnaceRecipeManager")
@Document("vanilla/api/recipe/manager/BlastFurnaceRecipeManager")
public class BlastFurnaceRecipeManager implements ICookingRecipeManager<BlastingRecipe> {
    
    @ZenCodeGlobals.Global("blastFurnace")
    public static final BlastFurnaceRecipeManager INSTANCE = new BlastFurnaceRecipeManager();
    
    private BlastFurnaceRecipeManager() {}
    
    @Override
    public RecipeHolder<BlastingRecipe> makeRecipe(String name, CookingBookCategory category, IItemStack output, IIngredient input, float xp, int cookTime) {
        
        return createHolder(fixRecipeId(name), new BlastingRecipe("", category, input.asVanillaIngredient(), output.getInternal(), xp, cookTime));
    }
    
    @Override
    public RecipeType<BlastingRecipe> getRecipeType() {
        
        return RecipeType.BLASTING;
    }
    
}
