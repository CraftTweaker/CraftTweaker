package com.blamejared.crafttweaker.gametest.truth;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.gametest.truth.subject.type.ingredient.IIngredientSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.ingredient.IItemStackSubject;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface IExpectIIngredient extends IExpect {
    
    default IIngredientSubject assertThat(@Nullable IIngredient target) {
        
        return expect().about(IIngredientSubject.factory()).that(target);
    }
    
    default IItemStackSubject assertThat(@Nullable IItemStack target) {
        
        return expect().about(IItemStackSubject.factory()).that(target);
    }
    
}
