package com.blamejared.crafttweaker.api.ingredient.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.IIngredientList")
@Document("vanilla/api/ingredient/type/IIngredientList")
public class IIngredientList implements IIngredient {
    
    private final IIngredient[] ingredients;
    
    @ZenCodeType.Constructor
    public IIngredientList(IIngredient[] ingredients) {
        
        this.ingredients = ingredients;
    }
    
    @Override
    public boolean isEmpty() {
        
        // Short circuit if ingredients is empty
        return ingredients.length == 0 || IIngredient.super.isEmpty();
    }
    
    @Override
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        
        for(IIngredient item : ingredients) {
            if(item.matches(stack, ignoreDamage)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return Services.REGISTRY.getIngredientList(Arrays.stream(ingredients)
                .map(IIngredient::asVanillaIngredient)
                .collect(Collectors.toList()));
    }
    
    @Override
    public String getCommandString() {
        
        return Arrays.stream(ingredients).map(IIngredient::getCommandString).collect(Collectors.joining(" | "));
    }
    
    @Override
    public IItemStack[] getItems() {
        
        List<IItemStack> stacks = new ArrayList<>();
        Arrays.stream(ingredients).map(ing -> Arrays.asList(ing.getItems())).forEach(stacks::addAll);
        return stacks.toArray(new IItemStack[0]);
    }
    
    public IIngredient[] getIngredients() {
        
        return ingredients;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        IIngredientList that = (IIngredientList) o;
        
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(ingredients, that.ingredients);
    }
    
    @Override
    public int hashCode() {
        
        return Arrays.hashCode(ingredients);
    }
    
}
