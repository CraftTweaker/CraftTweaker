package com.blamejared.crafttweaker.api.recipe.fun;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.fun.RecipeFunction1D")
@Document("vanilla/api/recipe/fun/RecipeFunction1D")
public interface RecipeFunction1D {
    
    @ZenCodeType.Method
    IItemStack process(IItemStack usualOut, IItemStack[] inputs);
    
}