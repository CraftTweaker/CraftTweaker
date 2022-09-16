package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;

import java.util.ArrayList;
import java.util.List;

public record ReplacementRequest<T>(
        IRecipeComponent<T> component,
        ITargetingStrategy strategy,
        DescriptivePredicate<T> target,
        DescriptiveUnaryOperator<T> replacer
) {
    
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
