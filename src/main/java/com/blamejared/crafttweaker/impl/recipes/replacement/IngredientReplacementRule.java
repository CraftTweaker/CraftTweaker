package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.impl.item.MCIngredientList;
import net.minecraft.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public final class IngredientReplacementRule implements IReplacementRule {
    private final IIngredient from;
    private final IIngredient to;
    
    private IngredientReplacementRule(final IIngredient from, final IIngredient to) {
        this.from = from;
        this.to = to;
    }
    
    public static IReplacementRule create(final IIngredient from, final IIngredient to) {
        return areTheSame(from, to)? IReplacementRule.EMPTY : new IngredientReplacementRule(from, to);
    }
    
    private static boolean areTheSame(final IIngredient a, final IIngredient b) {
        // TODO("Maybe a better equality check")
        return a == b || Objects.equals(a, b) || (a.contains(b) && b.contains(a));
    }
    
    @Override
    public <T> Optional<T> getReplacement(final T ingredient, final Class<? super T> type) {
        return IReplacementRule.chain(
                IReplacementRule.withType(ingredient, type, IIngredient.class, this::getIIngredientReplacement),
                IReplacementRule.withType(ingredient, type, Ingredient.class, this::getIngredientReplacement)
        );
    }
    
    private Optional<IIngredient> getIIngredientReplacement(final IIngredient original) {
        final IItemStack[] oldItems = original.getItems();
        final IIngredient[] newItems = Arrays.stream(oldItems)
                .map(this::getStackReplacement)
                .toArray(IIngredient[]::new);
    
        return Arrays.equals(oldItems, newItems)? Optional.empty() : Optional.of(new MCIngredientList(newItems));
    }
    
    private Optional<Ingredient> getIngredientReplacement(final Ingredient original) {
        return this.getIIngredientReplacement(IIngredient.fromIngredient(original)).map(IIngredient::asVanillaIngredient);
    }
    
    private IIngredient getStackReplacement(final IItemStack original) {
        return this.from.matches(original)? this.to : original;
    }
    
    @Override
    public String describe() {
        return String.format("Replacing %s --> %s", this.from.getCommandString(), this.to.getCommandString());
    }
    
}
