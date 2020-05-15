package com.blamejared.crafttweaker.impl.recipes.wrappers;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import net.minecraft.item.crafting.IRecipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipes.WrapperRecipe")
public class WrapperRecipe {
    
    private final IRecipe<?> recipe;
    
    public WrapperRecipe(IRecipe<?> recipe) {
        this.recipe = recipe;
    }
    
    @ZenCodeType.Getter("id")
    public String getId() {
        return getRecipe().getId().toString();
    }
    
    @ZenCodeType.Getter("group")
    public String getGroup() {
        return getRecipe().getGroup();
    }
    
    @ZenCodeType.Getter("output")
    public IItemStack getOutput() {
        return new MCItemStack(getRecipe().getRecipeOutput());
    }
    
    @ZenCodeType.Getter("dynamic")
    public boolean isDynamic() {
        return getRecipe().isDynamic();
    }
    
    @ZenCodeType.Method
    public boolean canFit(int width, int height) {
        return getRecipe().canFit(width, height);
    }
    
    @ZenCodeType.Getter("icon")
    public IItemStack getIcon() {
        return new MCItemStackMutable(getRecipe().getIcon());
    }
    
    @ZenCodeType.Getter("ingredients")
    public List<IIngredient> getIngredients() {
        return getRecipe().getIngredients().stream().map(IIngredient::fromIngredient).collect(Collectors.toList());
    }
    
    public IRecipe<?> getRecipe() {
        return recipe;
    }
}
