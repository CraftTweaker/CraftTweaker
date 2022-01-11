package com.blamejared.crafttweaker.api.recipe.replacement.rule;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

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
        
        return areTheSame(from, to) ? IReplacementRule.EMPTY : new IngredientReplacementRule(from, to);
    }
    
    private static boolean areTheSame(final IIngredient a, final IIngredient b) {
        // TODO("Maybe a better equality check")
        return a == b || Objects.equals(a, b) || (a.contains(b) && b.contains(a));
    }
    
    @Override
    public <T, U extends Recipe<?>> Optional<T> getReplacement(final T ingredient, final Class<T> type, final U recipe) {
        
        return IReplacementRule.chain(
                IReplacementRule.withType(ingredient, type, recipe, IIngredient.class, this::getIIngredientReplacement),
                IReplacementRule.withType(ingredient, type, recipe, Ingredient.class, this::getIngredientReplacement)
        );
    }
    
    private <U extends Recipe<?>> Optional<IIngredient> getIIngredientReplacement(final IIngredient original, final U recipe) {
        
        final IItemStack[] oldItems = original.getItems();
        final IIngredient[] newItems = Arrays.stream(oldItems)
                .map(this::getStackReplacement)
                .toArray(IIngredient[]::new);
        
        return Arrays.equals(oldItems, newItems) ? Optional.empty() : Optional.of(new IIngredientList(newItems));
    }
    
    private <U extends Recipe<?>> Optional<Ingredient> getIngredientReplacement(final Ingredient original, final U recipe) {
        
        return this.getIIngredientReplacement(IIngredient.fromIngredient(original), recipe)
                .map(IIngredient::asVanillaIngredient);
    }
    
    private IIngredient getStackReplacement(final IItemStack original) {
        
        return this.from.matches(original) ? this.to : original;
    }
    
    @Override
    public String describe() {
        
        return String.format("Replacing %s --> %s", this.from.getCommandString(), this.to.getCommandString());
    }
    
}
