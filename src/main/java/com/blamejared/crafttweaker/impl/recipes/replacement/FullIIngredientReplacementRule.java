package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;

import java.util.Objects;
import java.util.Optional;

public final class FullIIngredientReplacementRule implements IReplacementRule<IIngredient> {
    private final IIngredient from;
    private final IIngredient to;
    
    private FullIIngredientReplacementRule(final IIngredient from, final IIngredient to) {
        this.from = from;
        this.to = to;
    }
    
    public static IReplacementRule<IIngredient> create(final IIngredient from, final IIngredient to) {
        return areTheSame(from, to)? IReplacementRule.EMPTY.cast() : new FullIIngredientReplacementRule(from, to);
    }
    
    private static boolean areTheSame(final IIngredient a, final IIngredient b) {
        // TODO("Maybe a better equality check")
        return a == b || Objects.equals(a, b) || (a.contains(b) && b.contains(a));
    }
    
    @Override
    public Optional<IIngredient> getReplacement(final IIngredient initial) {
        return areTheSame(this.from, initial)? Optional.of(this.to) : Optional.empty();
    }
    
    @Override
    public String describe() {
        return String.format("%s --> %s", this.from.getCommandString(), this.to.getCommandString());
    }
    
    @Override
    public Class<IIngredient> getTargetedType() {
        return IIngredient.class;
    }
    
}
