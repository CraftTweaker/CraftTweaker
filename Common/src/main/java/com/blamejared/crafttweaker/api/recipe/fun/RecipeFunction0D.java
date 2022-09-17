package com.blamejared.crafttweaker.api.recipe.fun;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.fun.RecipeFunction0D")
@Document("vanilla/api/recipe/fun/RecipeFunction0D")
public interface RecipeFunction0D {
    
    @ZenCodeType.Method
    IItemStack process(IItemStack usualOut, IItemStack inputs);
    
}