package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.ITargetingRule;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ExcludingRecipesAndDelegatingTargetingRule implements ITargetingRule {
    private final ITargetingRule delegate;
    private final Collection<ResourceLocation> exclusions;
    
    private ExcludingRecipesAndDelegatingTargetingRule(final ITargetingRule delegate, final Collection<ResourceLocation> exclusions) {
        this.delegate = delegate;
        this.exclusions = exclusions;
    }
    
    public static ExcludingRecipesAndDelegatingTargetingRule of(final ITargetingRule delegate, final Collection<ResourceLocation> exclusions) {
        Objects.requireNonNull(delegate);
        if (exclusions.isEmpty()) {
            throw new IllegalArgumentException("Unable to create an exclusion for recipes without any recipe to exclude");
        }
        if (delegate instanceof ExcludingRecipesAndDelegatingTargetingRule) {
            final ExcludingRecipesAndDelegatingTargetingRule delegatingRule = (ExcludingRecipesAndDelegatingTargetingRule) delegate;
            return of(delegatingRule.delegate, Util.make(new HashSet<>(exclusions), it -> it.addAll(delegatingRule.exclusions)));
        }
        return new ExcludingRecipesAndDelegatingTargetingRule(delegate, exclusions);
    }
    
    public static ExcludingRecipesAndDelegatingTargetingRule of(final ITargetingRule delegate, final ResourceLocation... exclusions) {
        return of(delegate, new HashSet<>(Arrays.asList(exclusions)));
    }
    
    public static ExcludingRecipesAndDelegatingTargetingRule of(final ITargetingRule delegate, final WrapperRecipe... exclusions) {
        return of(delegate, Arrays.stream(exclusions).map(WrapperRecipe::getRecipe).map(IRecipe::getId).collect(Collectors.toSet()));
    }
    
    @Override
    public boolean shouldBeReplaced(final IRecipe<?> recipe, final IRecipeManager manager) {
        return !this.exclusions.contains(recipe.getId()) && this.delegate.shouldBeReplaced(recipe, manager);
    }
    
    @Override
    public String describe() {
        return String.format(
                "%s, but excluding recipes {%s}",
                this.delegate.describe(),
                this.exclusions.stream()
                        .map(ResourceLocation::toString)
                        .collect(Collectors.joining(", "))
        );
    }
    
}
