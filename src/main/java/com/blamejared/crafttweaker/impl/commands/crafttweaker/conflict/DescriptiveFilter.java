package com.blamejared.crafttweaker.impl.commands.crafttweaker.conflict;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.function.Predicate;

final class DescriptiveFilter implements Predicate<Map.Entry<ResourceLocation, IRecipe<?>>> {
    
    private final Predicate<IRecipe<?>> delegate;
    private final String description;
    
    private DescriptiveFilter(final Predicate<IRecipe<?>> delegate, final String description) {
        
        this.delegate = delegate;
        this.description = description;
    }
    
    static DescriptiveFilter of() {
        
        return new DescriptiveFilter(it -> true, "");
    }
    
    static DescriptiveFilter of(final IRecipeManager manager) {
        
        final IRecipeType<?> type = manager.getRecipeType();
        return new DescriptiveFilter(it -> it.getType() == type, " for type " + manager.getCommandString());
    }
    
    static DescriptiveFilter of(final ItemStack stack) {
        
        return new DescriptiveFilter(it -> ItemStackHelper.areStacksTheSame(it.getRecipeOutput(), stack), " for output " + ItemStackHelper.getCommandString(stack));
    }
    
    @Override
    public boolean test(final Map.Entry<ResourceLocation, IRecipe<?>> recipe) {
        
        return this.delegate.test(recipe.getValue());
    }
    
    String description() {
        
        return this.description;
    }
}
