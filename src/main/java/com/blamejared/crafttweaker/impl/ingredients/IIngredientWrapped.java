package com.blamejared.crafttweaker.impl.ingredients;

import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import net.minecraft.item.crafting.*;

import java.util.*;

public class IIngredientWrapped implements IIngredient {
    
    private final Ingredient ingredient;
    private final String commandString;
    
    public IIngredientWrapped(Ingredient ingredient, String commandString) {
        this.ingredient = ingredient;
        this.commandString = commandString;
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
        return Arrays.stream(ingredient.getMatchingStacks())
                .map(MCItemStack::new)
                .toArray(IItemStack[]::new);
    }
}
