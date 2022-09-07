package com.blamejared.crafttweaker.api.recipe.func;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.func.RecipeFunctionSingle")
@Document("vanilla/api/recipe/func/RecipeFunctionSingle")
public interface RecipeFunctionSingle {
    
    @ZenCodeType.Method
    IItemStack process(IItemStack usualOut, IItemStack inputs);
    
}