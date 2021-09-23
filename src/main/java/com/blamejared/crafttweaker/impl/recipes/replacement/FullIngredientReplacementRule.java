package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;

import java.util.Objects;
import java.util.Optional;

public final class FullIngredientReplacementRule implements IReplacementRule {
    
    private final IIngredient from;
    private final IIngredient to;
    
    private FullIngredientReplacementRule(final IIngredient from, final IIngredient to) {
        
        this.from = from;
        this.to = to;
    }
    
    public static IReplacementRule create(final IIngredient from, final IIngredient to) {
        
        return areTheSame(from, to) ? IReplacementRule.EMPTY : new FullIngredientReplacementRule(from, to);
    }
    
    private static boolean areTheSame(final IIngredient a, final IIngredient b) {
        // TODO("Maybe a better equality check")
        return a == b || Objects.equals(a, b) || (a.contains(b) && b.contains(a));
    }
    
    @Override
    public <T, U extends IRecipe<?>> Optional<T> getReplacement(final T ingredient, final Class<T> type, final U recipe) {
        
        return IReplacementRule.chain(
                IReplacementRule.withType(ingredient, type, recipe, IIngredient.class, this::getIIngredientReplacement),
                IReplacementRule.withType(ingredient, type, recipe, Ingredient.class, this::getIngredientReplacement)
        );
    }
    
    private <U extends IRecipe<?>> Optional<IIngredient> getIIngredientReplacement(final IIngredient ingredient, final U recipe) {
        
        return areTheSame(this.from, ingredient) ? Optional.of(this.to) : Optional.empty();
    }
    
    private <U extends IRecipe<?>> Optional<Ingredient> getIngredientReplacement(final Ingredient ingredient, final U recipe) {
        
        return this.getIIngredientReplacement(IIngredient.fromIngredient(ingredient), recipe)
                .map(IIngredient::asVanillaIngredient);
    }
    
    @Override
    public String describe() {
        
        return String.format("Replacing fully %s --> %s", this.from.getCommandString(), this.to.getCommandString());
    }
    
}
