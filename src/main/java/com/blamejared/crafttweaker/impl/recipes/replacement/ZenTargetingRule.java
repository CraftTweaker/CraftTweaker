package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.ITargetingRule;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import net.minecraft.item.crafting.IRecipe;

import java.util.function.BiPredicate;

public final class ZenTargetingRule implements ITargetingRule {
    
    private final BiPredicate<WrapperRecipe, IRecipeManager> function;
    
    private ZenTargetingRule(final BiPredicate<WrapperRecipe, IRecipeManager> function) {
        
        this.function = function;
    }
    
    public static ZenTargetingRule of(final BiPredicate<WrapperRecipe, IRecipeManager> function) {
        
        return new ZenTargetingRule(function);
    }
    
    @Override
    public boolean shouldBeReplaced(final IRecipe<?> recipe, final IRecipeManager manager) {
        
        return this.function.test(new WrapperRecipe(recipe), manager);
    }
    
    @Override
    public String describe() {
        
        return "a custom set of recipes";
    }
    
}
