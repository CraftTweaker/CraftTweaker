package com.blamejared.crafttweaker.api.ingredient.type;


import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public abstract class IngredientCraftTweaker<T extends IIngredient> implements CustomIngredient, IngredientCraftTweakerBase {
    
    private final T crtIngredient;
    
    protected IngredientCraftTweaker(T crtIngredient) {
        
        this.crtIngredient = crtIngredient;
    }
    
    @Override
    public boolean test(ItemStack stack) {
        
        return IngredientCraftTweakerBase.super.test(stack);
    }
    
    @Override
    public T getCrTIngredient() {
        
        return crtIngredient;
    }
    
    @Override
    public List<ItemStack> getMatchingStacks() {
        
        return Arrays.stream(getCrTIngredient().getItems()).map(IItemStack::getInternal).toList();
    }
    
    @Override
    public boolean requiresTesting() {
        
        return true;
    }
    
    
}
