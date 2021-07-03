package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.ITargetingRule;
import net.minecraft.item.crafting.IRecipe;

public final class EverythingTargetingRule implements ITargetingRule {
    private EverythingTargetingRule() {}
    
    public static EverythingTargetingRule of() {
        return new EverythingTargetingRule();
    }
    
    @Override
    public boolean shouldBeReplaced(final IRecipe<?> recipe, final IRecipeManager manager) {
        return true;
    }
    
    @Override
    public String describe() {
        return "everything";
    }
    
}
