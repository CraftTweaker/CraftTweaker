package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.impl.item.MCIngredientList;
import net.minecraft.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.Optional;

public final class StackTargetingReplacementRule implements IReplacementRule {
    private final IItemStack from;
    private final IItemStack to;
    
    private StackTargetingReplacementRule(final IItemStack from, final IItemStack to) {
        this.from = from.copy();
        this.to = to.copy();
    }
    
    public static IReplacementRule create(final IItemStack from, final IItemStack to) {
        return from.matches(to)? IReplacementRule.EMPTY : new StackTargetingReplacementRule(from, to);
    }
    
    @Override
    public <T> Optional<T> getReplacement(final T ingredient, final Class<? super T> type) {
        return IReplacementRule.chain(
                IReplacementRule.withType(ingredient, type, IIngredient.class, this::getIIngredientReplacement),
                IReplacementRule.withType(ingredient, type, Ingredient.class, this::getIngredientReplacement)
        );
    }
    
    private Optional<IIngredient> getIIngredientReplacement(final IIngredient ingredient) {
        final IItemStack[] oldItems = ingredient.getItems();
        final IItemStack[] newItems = Arrays.stream(oldItems)
                .map(this::getStackReplacement)
                .toArray(IItemStack[]::new);
        
        return Arrays.equals(oldItems, newItems)? Optional.empty() : Optional.of(new MCIngredientList(newItems));
    }
    
    private Optional<Ingredient> getIngredientReplacement(final Ingredient ingredient) {
        return this.getIIngredientReplacement(IIngredient.fromIngredient(ingredient)).map(IIngredient::asVanillaIngredient);
    }
    
    private IItemStack getStackReplacement(final IItemStack original) {
        return this.from.matches(original)? this.to : original;
    }
    
    @Override
    public String describe() {
        return String.format("Replacing stacks: %s --> %s", this.from.getCommandString(), this.to.getCommandString());
    }
    
}
