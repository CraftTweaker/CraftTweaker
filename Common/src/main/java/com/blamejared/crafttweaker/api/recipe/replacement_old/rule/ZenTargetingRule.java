package com.blamejared.crafttweaker.api.recipe.replacement_old.rule;


import com.blamejared.crafttweaker.api.recipe.handler.ITargetingRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.BiPredicate;

public final class ZenTargetingRule implements ITargetingRule {
    
    private final BiPredicate<Recipe<?>, IRecipeManager<?>> function;
    
    private ZenTargetingRule(final BiPredicate<Recipe<?>, IRecipeManager<?>> function) {
        
        this.function = function;
    }
    
    public static ZenTargetingRule of(final BiPredicate<Recipe<?>, IRecipeManager<?>> function) {
        
        return new ZenTargetingRule(function);
    }
    
    @Override
    public boolean shouldBeReplaced(final Recipe<?> recipe, final IRecipeManager<?> manager) {
        
        return this.function.test(recipe, manager);
    }
    
    @Override
    public String describe() {
        
        return "a custom set of recipes";
    }
    
}
