package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.IngredientList;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IngredientList")
@Document("vanilla/api/items/IngredientList")
public class MCIngredientList implements IIngredient {
    
    private final IIngredient[] ingredients;
    
    public MCIngredientList(IIngredient[] ingredients) {
    
        this.ingredients = ingredients;
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
    
        return new IngredientList(Arrays.stream(ingredients)
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
    
        MCIngredientList that = (MCIngredientList) o;
    
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(ingredients, that.ingredients);
    }
    
    @Override
    public int hashCode() {
    
        return Arrays.hashCode(ingredients);
    }
    
}
