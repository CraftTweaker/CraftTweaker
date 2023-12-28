package com.blamejared.crafttweaker.api.recipe.manager;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.ICookingRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this furnace
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.FurnaceRecipeManager")
@Document("vanilla/api/recipe/manager/FurnaceRecipeManager")
public class FurnaceRecipeManager implements ICookingRecipeManager<SmeltingRecipe> {
    
    @ZenCodeGlobals.Global("furnace")
    public static final FurnaceRecipeManager INSTANCE = new FurnaceRecipeManager();
    
    private FurnaceRecipeManager() {}
    
    @Override
    public RecipeHolder<SmeltingRecipe> makeRecipe(String name, CookingBookCategory category, IItemStack output, IIngredient input, float xp, int cookTime) {
        
        return createHolder(fixRecipeId(name), new SmeltingRecipe("", category, input.asVanillaIngredient(), output.getInternal(), xp, cookTime));
    }
    
    @Override
    public RecipeType<SmeltingRecipe> getRecipeType() {
        
        return RecipeType.SMELTING;
    }
    
}
