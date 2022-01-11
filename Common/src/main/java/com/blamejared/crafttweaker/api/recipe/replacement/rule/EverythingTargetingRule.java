package com.blamejared.crafttweaker.api.recipe.replacement.rule;


import com.blamejared.crafttweaker.api.recipe.handler.ITargetingRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.Recipe;

public final class EverythingTargetingRule implements ITargetingRule {
    
    private EverythingTargetingRule() {}
    
    public static EverythingTargetingRule of() {
        
        return new EverythingTargetingRule();
    }
    
    @Override
    public boolean shouldBeReplaced(final Recipe<?> recipe, final IRecipeManager<?> manager) {
        
        return true;
    }
    
    @Override
    public String describe() {
        
        return "everything";
    }
    
}
