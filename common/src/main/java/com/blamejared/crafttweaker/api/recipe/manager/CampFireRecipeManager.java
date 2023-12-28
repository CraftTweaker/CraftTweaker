package com.blamejared.crafttweaker.api.recipe.manager;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.ICookingRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this campfire
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.CampFireRecipeManager")
@Document("vanilla/api/recipe/manager/CampFireRecipeManager")
public class CampFireRecipeManager implements ICookingRecipeManager<CampfireCookingRecipe> {
    
    @ZenCodeGlobals.Global("campfire")
    public static final CampFireRecipeManager INSTANCE = new CampFireRecipeManager();
    
    private CampFireRecipeManager() {}
    
    @Override
    public RecipeHolder<CampfireCookingRecipe> makeRecipe(String name, CookingBookCategory category, IItemStack output, IIngredient input, float xp, int cookTime) {
        
        return createHolder(fixRecipeId(name), new CampfireCookingRecipe("", category, input.asVanillaIngredient(), output.getInternal(), xp, cookTime));
    }
    
    @Override
    public RecipeType<CampfireCookingRecipe> getRecipeType() {
        
        return RecipeType.CAMPFIRE_COOKING;
    }
    
}
