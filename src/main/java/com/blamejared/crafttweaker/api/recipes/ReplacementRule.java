package com.blamejared.crafttweaker.api.recipes;

import com.blamejared.crafttweaker.api.item.IIngredient;
import net.minecraft.item.crafting.Ingredient;

import java.util.Objects;

// TODO("Make this more than a data class")
public final class ReplacementRule {
    private final IIngredient from;
    private final IIngredient to;
    
    public ReplacementRule(final IIngredient from, final IIngredient to) {
        this.from = from;
        this.to = to;
    }
    
    public IIngredient getFrom() {
        return this.from;
    }
    
    public IIngredient getTo() {
        return this.to;
    }
    
    public Ingredient getVanillaFrom() {
        return this.from.asVanillaIngredient();
    }
    
    public Ingredient getVanillaTo() {
        return this.to.asVanillaIngredient();
    }
    
    public boolean shouldReplace(final IIngredient ingredient) {
        return this.from.contains(ingredient);
    }
    
    public boolean shouldReplace(final Ingredient ingredient) {
        return this.shouldReplace(IIngredient.fromIngredient(ingredient));
    }
    
    public String describe() {
        return String.format("%s -> %s", this.from.getCommandString(), this.to.getCommandString());
    }
    
    @Override
    public String toString() {
        return this.describe();
    }
    
    // TODO("Better equals")
    @Override
    public boolean equals(final Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        final ReplacementRule that = (ReplacementRule) o;
        return this.from.equals(that.from) && this.to.equals(that.to);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.from, this.to);
    }
    
}
