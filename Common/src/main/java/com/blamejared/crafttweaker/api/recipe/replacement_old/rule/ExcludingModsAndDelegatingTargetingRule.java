package com.blamejared.crafttweaker.api.recipe.replacement_old.rule;


import com.blamejared.crafttweaker.api.recipe.handler.ITargetingRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ExcludingModsAndDelegatingTargetingRule implements ITargetingRule {
    
    private final ITargetingRule delegate;
    private final Collection<String> exclusions;
    
    private ExcludingModsAndDelegatingTargetingRule(final ITargetingRule delegate, final Collection<String> exclusions) {
        
        this.delegate = delegate;
        this.exclusions = exclusions;
    }
    
    public static ExcludingModsAndDelegatingTargetingRule of(final ITargetingRule delegate, final Collection<String> exclusions) {
        
        Objects.requireNonNull(delegate);
        if(exclusions.isEmpty()) {
            throw new IllegalArgumentException("Unable to create an exclusion for mods without any mods to exclude");
        }
        if(delegate instanceof ExcludingModsAndDelegatingTargetingRule) {
            final ExcludingModsAndDelegatingTargetingRule delegatingRule = (ExcludingModsAndDelegatingTargetingRule) delegate;
            return of(delegatingRule.delegate, Util.make(new HashSet<>(exclusions), it -> it.addAll(delegatingRule.exclusions)));
        }
        return new ExcludingModsAndDelegatingTargetingRule(delegate, exclusions);
    }
    
    public static ExcludingModsAndDelegatingTargetingRule of(final ITargetingRule delegate, final String... exclusions) {
        
        return of(delegate, new HashSet<>(Arrays.asList(exclusions)));
    }
    
    public static ExcludingModsAndDelegatingTargetingRule of(final ITargetingRule delegate, final Recipe... exclusions) {
        
        return of(delegate, Arrays.stream(exclusions)
                .map(Recipe::getId)
                .map(ResourceLocation::getNamespace)
                .collect(Collectors.toSet()));
    }
    
    @Override
    public boolean shouldBeReplaced(final Recipe<?> recipe, final IRecipeManager<?> manager) {
        
        return !this.exclusions.contains(recipe.getId()
                .getNamespace()) && this.delegate.shouldBeReplaced(recipe, manager);
    }
    
    @Override
    public String describe() {
        
        return String.format(
                "%s, but excluding mods {%s}",
                this.delegate.describe(),
                String.join(", ", this.exclusions)
        );
    }
    
}
