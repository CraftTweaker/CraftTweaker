package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Indicates a request that needs to be carried out by the {@link Replacer}.
 *
 * @param component The {@link IRecipeComponent} for which this replacer should be carried out.
 * @param strategy  The {@link ITargetingStrategy} that indicates how the components should be targeted.
 * @param target    A {@link java.util.function.Predicate} that determines the element that will be targeted for
 *                  replacement.
 * @param replacer  A {@link java.util.function.UnaryOperator} that is responsible for performing the actual
 *                  replacement.
 * @param <T>       The type of the object that will undergo replacement.
 *
 * @since 10.0.0
 */
public record ReplacementRequest<T>(
        IRecipeComponent<T> component,
        ITargetingStrategy strategy,
        DescriptivePredicate<T> target,
        DescriptiveUnaryOperator<T> replacer
) {
    
    /**
     * Applies this request to the given {@link IDecomposedRecipe}, if possible.
     *
     * <p>If the {@linkplain IDecomposedRecipe#components() recipe's components} do not contain the
     * {@link #component()} this request is targeting, then replacement fails immediately. Otherwise, a best-effort
     * attempt shall be performed.</p>
     *
     * @param recipe The recipe that should undergo replacement.
     *
     * @return Whether the replacement was successful or not.
     *
     * @since 10.0.0
     */
    public boolean applyRequest(final IDecomposedRecipe recipe) {
        
        final List<T> object = recipe.get(this.component());
        
        if(object == null || object.isEmpty()) {
            return false;
        }
        
        final List<T> newList = new ArrayList<>(object);
        final boolean replaced = this.applyToEach(object);
        
        if(replaced) {
            recipe.set(this.component(), newList);
        }
        
        return replaced;
    }
    
    /**
     * Describes this request in a human-readable format for log output.
     *
     * @return A human-readable description.
     *
     * @since 10.0.0
     */
    public String describe() {
        
        return "replacing component %s matching %s according to %s with %s".formatted(
                this.component().getCommandString(),
                this.target().describe(),
                this.strategy().getCommandString(),
                this.replacer().describe()
        );
    }
    
    private boolean applyToEach(final List<T> elements) {
        
        boolean any = false;
        
        for(int i = 0, s = elements.size(); i < s; ++i) {
            final T old = elements.get(i);
            final T replaced = this.tryToReplace(old);
            if(replaced != null) {
                any = true;
                elements.set(i, replaced);
            }
        }
        
        return any;
    }
    
    private T tryToReplace(final T object) {
        
        return this.strategy().castStrategy(this.component(), object, this::replace);
    }
    
    private T replace(final T object) {
        
        return this.target().test(object) ? this.replacer().apply(object) : null;
    }
    
}
