package com.blamejared.crafttweaker.impl.recipe.replacement;

import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class DefaultTargetingStrategies {
    
    private DefaultTargetingStrategies() {}
    
    // TODO("we need to find proper names for these strategies")
    
    @ZenCodeType.Nullable
    public static <T> T plain(@SuppressWarnings("unused") final IRecipeComponent<T> component, final T object, final Function<T, @ZenCodeType.Nullable T> replacer) {
        
        return replacer.apply(object);
    }
    
    @ZenCodeType.Nullable
    public static <T> T deep(final IRecipeComponent<T> component, final T object, final Function<T, @ZenCodeType.Nullable T> replacer) {
        
        final List<T> components = new ArrayList<>(component.unwrap(object));
        
        boolean any = false;
        for(int i = 0, s = components.size(); i < s; ++i) {
            final T replaced = replacer.apply(components.get(i));
            if(replaced != null) {
                any = true;
                components.set(i, replaced);
            }
        }
        
        return any ? component.wrap(components) : null;
    }
    
}
