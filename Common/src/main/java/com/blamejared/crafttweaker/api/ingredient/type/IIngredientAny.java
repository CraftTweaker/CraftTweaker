package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

/**
 * An IIngredient which matches all items
 *
 * @docParam this IIngredientAny.getInstance()
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.IIngredientAny")
@Document("vanilla/api/ingredient/type/IIngredientAny")
public enum IIngredientAny implements IIngredient {
    
    INSTANCE;
    
    @ZenCodeType.Method
    public static IIngredientAny getInstance() {
        
        return INSTANCE;
    }
    
    @Override
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        
        return stack != null && !stack.isEmpty();
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return Services.REGISTRY.getIngredientAny();
    }
    
    @Override
    public String getCommandString() {
        
        return "IIngredientAny.getInstance()";
    }
    
    @Override
    public IItemStack[] getItems() {
        
        return new IItemStack[0];
    }
    
}
