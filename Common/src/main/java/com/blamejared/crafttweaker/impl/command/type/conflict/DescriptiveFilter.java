package com.blamejared.crafttweaker.impl.command.type.conflict;


import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Map;
import java.util.function.Predicate;

final class DescriptiveFilter implements Predicate<Map.Entry<ResourceLocation, RecipeHolder<?>>> {
    
    private final Predicate<RecipeHolder<?>> delegate;
    private final MutableComponent description;
    
    private DescriptiveFilter(final Predicate<RecipeHolder<?>> delegate, final MutableComponent description) {
        
        this.delegate = delegate;
        this.description = description;
    }
    
    static DescriptiveFilter of() {
        
        return new DescriptiveFilter(it -> true, Component.literal(""));
    }
    
    static DescriptiveFilter of(final IRecipeManager<?> manager) {
        
        final RecipeType<?> type = manager.getRecipeType();
        return new DescriptiveFilter(it -> it.value().getType() == type, Component.translatable("crafttweaker.command.conflict.description.type", manager.getCommandString()));
    }
    
    static DescriptiveFilter of(final ItemStack stack) {
        
        return new DescriptiveFilter(it -> ItemStackUtil.areStacksTheSame(AccessibleElementsProvider.get().registryAccess(it.value()::getResultItem), stack), Component.translatable("crafttweaker.command.conflict.description.output", ItemStackUtil.getCommandString(stack)));
    }
    
    @Override
    public boolean test(final Map.Entry<ResourceLocation, RecipeHolder<?>> recipe) {
        
        return this.delegate.test(recipe.getValue());
    }
    
    MutableComponent description() {
        
        return this.description;
    }
    
}
