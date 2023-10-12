package com.blamejared.crafttweaker.api.ingredient.type;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientList;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
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
    
    public static final ResourceLocation ID = CraftTweakerConstants.rl("list");
    
    private final IIngredient[] ingredients;
    
    @ZenCodeType.Constructor
    public IIngredientList(IIngredient[] ingredients) {
        
        this.ingredients = flatten(ingredients);
    }
    
    private static IIngredient[] flatten(final IIngredient[] ingredients) {
        
        if(ingredients.length < 2) {
            return ingredients; // why even have a list?
        }
        
        // Acts as buffer: same length as a rough guess
        IIngredient[] result = new IIngredient[ingredients.length];
        int actualLength = 0;
        
        for(final IIngredient ingredient : ingredients) {
            if(ingredient instanceof IIngredientList list) {
                final IIngredient[] within = list.ingredients; // Flattened by default
                final int length = actualLength + within.length;
                
                if(length == actualLength) {
                    continue;
                }
                
                if(length > result.length) {
                    result = Arrays.copyOf(result, length);
                }
                
                System.arraycopy(within, 0, result, actualLength, within.length);
                actualLength = length;
                
                continue;
            }
            
            if(ingredient.isEmpty()) {
                continue;
            }
            
            result[actualLength++] = ingredient;
            
            if(actualLength == result.length) {
                result = Arrays.copyOf(result, actualLength * 3 / 2);
            }
        }
        
        return Arrays.copyOf(result, actualLength);
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
    public IIngredientList or(final IIngredient other) {
        
        final IIngredient[] newArray = Arrays.copyOf(this.ingredients, this.ingredients.length + 1);
        newArray[this.ingredients.length] = other;
        return new IIngredientList(newArray);
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return IngredientList.ingredient(Arrays.stream(ingredients)
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
    
    @Override
    public String toString() {
        
        return this.getCommandString();
    }
    
}
