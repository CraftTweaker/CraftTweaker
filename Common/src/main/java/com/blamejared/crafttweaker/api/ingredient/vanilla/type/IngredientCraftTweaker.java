package com.blamejared.crafttweaker.api.ingredient.vanilla.type;


import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientCraftTweakerBase;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class IngredientCraftTweaker<T extends IIngredient> implements CraftTweakerVanillaIngredient, IngredientCraftTweakerBase {
    
    private final T crtIngredient;
    
    protected IngredientCraftTweaker(T crtIngredient) {
        
        this.crtIngredient = crtIngredient;
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
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
    
    @Override
    public boolean isEmpty() {
        
        return getCrTIngredient().isEmpty();
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientCraftTweaker<?> that = (IngredientCraftTweaker<?>) o;
        return Objects.equals(crtIngredient, that.crtIngredient);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(crtIngredient);
    }
    
}
