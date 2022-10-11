package com.blamejared.crafttweaker.api.recipe.replacement_old.rule;


import com.blamejared.crafttweaker.api.recipe.handler.ITargetingRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public final class SpecificModsTargetingRule implements ITargetingRule {
    
    private final Collection<String> mods;
    
    private SpecificModsTargetingRule(final Collection<String> mods) {
        
        this.mods = mods;
    }
    
    public static SpecificModsTargetingRule of(final Collection<String> mods) {
        
        if(mods.isEmpty()) {
            throw new IllegalArgumentException("Unable to create a specific mods targeting rule without any targets");
        }
        return new SpecificModsTargetingRule(mods);
    }
    
    public static SpecificModsTargetingRule of(final String... mods) {
        
        return of(new HashSet<>(Arrays.asList(mods)));
    }
    
    @Override
    public boolean shouldBeReplaced(final Recipe<?> recipe, final IRecipeManager<?> manager) {
        
        return this.mods.contains(recipe.getId().getNamespace());
    }
    
    @Override
    public String describe() {
        
        return this.mods.stream().collect(Collectors.joining(", ", "recipes from mods {", "}"));
    }
    
}
