package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
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
    
    private IIngredient[] ingredients;
    
    public MCIngredientList(IIngredient[] ingredients) {
        this.ingredients = ingredients;
    }
    
    @Override
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        for(IIngredient item : ingredients) {
            if(item.matches(stack, ignoreDamage)){
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
        return Arrays.stream(ingredients).map(IIngredient::getCommandString).collect(Collectors.joining(" | "));
    }
    
    @Override
    public IItemStack[] getItems() {
        List<IItemStack> stacks = new ArrayList<>();
        Arrays.stream(ingredients).map(ing -> Arrays.asList(ing.getItems())).forEach(stacks::addAll);
        return stacks.toArray(new IItemStack[0]);
    }
}
