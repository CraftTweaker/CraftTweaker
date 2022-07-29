package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.IIngredientEmpty")
@Document("vanilla/api/ingredient/type/IIngredientEmpty")
public enum IIngredientEmpty implements IIngredient {
    INSTANCE;
    
    @ZenCodeType.Method
    public static IIngredientEmpty getInstance() {
        
        return INSTANCE;
    }
    
    @Override
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        
        return stack.isEmpty();
    }
    
    @Override
    public boolean isEmpty() {
        
        return true;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return Ingredient.EMPTY;
    }
    
    @Override
    public String getCommandString() {
        
        return "IIngredientEmpty.getInstance()";
    }
    
    @Override
    public IItemStack[] getItems() {
        
        return new IItemStack[0];
    }
}
