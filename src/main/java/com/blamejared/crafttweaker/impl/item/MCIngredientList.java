package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MCIngredientList implements IIngredient {
    
    private IIngredient[] ingredients;
    
    public MCIngredientList(IIngredient[] ingredients) {
        this.ingredients = ingredients;
    }
    
    @Override
    public boolean matches(IItemStack stack) {
        for(IIngredient item : ingredients) {
            if(item.matches(stack)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        return Ingredient.merge(Arrays.stream(ingredients).map(IIngredient::asVanillaIngredient).collect(Collectors.toList()));
    }
    
    @Override
    public String getCommandString() {
        // TODO not sure what to do here, since technically speaking there is no way to use it from a script yet
        return "";
    }
    
    @Override
    public IItemStack[] getItems() {
        List<IItemStack> stacks = new ArrayList<>();
        Arrays.stream(ingredients).map(ing -> Arrays.asList(ing.getItems())).forEach(stacks::addAll);
        return stacks.toArray(new IItemStack[0]);
    }
}
