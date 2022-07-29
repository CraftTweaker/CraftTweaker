package com.blamejared.crafttweaker.api.ingredient.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

/**
 * An IIngredient that wraps a vanilla Ingredient.
 *
 * @docParam this <tag:items:minecraft:wool>.asIIngredient();
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.WrappingIIngredient")
@Document("vanilla/api/ingredient/type/WrappingIIngredient")
public class WrappingIIngredient implements IIngredient {
    
    private final Ingredient ingredient;
    private final String commandString;
    
    public WrappingIIngredient(Ingredient ingredient, String commandString) {
        
        this.ingredient = ingredient;
        this.commandString = commandString;
    }
    
    @Override
    public boolean isEmpty() {
        
        return ingredient.isEmpty();
    }
    
    @Override
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        
        if(!ignoreDamage) {
            return ingredient.test(stack.getInternal());
        }
        return Arrays.stream(getItems()).anyMatch(item -> item.matches(stack, true));
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return ingredient;
    }
    
    @Override
    public String getCommandString() {
        
        return commandString;
    }
    
    @Override
    public IItemStack[] getItems() {
        
        return Arrays.stream(ingredient.getItems())
                .map(Services.PLATFORM::createMCItemStack)
                .toArray(IItemStack[]::new);
    }
    
}
