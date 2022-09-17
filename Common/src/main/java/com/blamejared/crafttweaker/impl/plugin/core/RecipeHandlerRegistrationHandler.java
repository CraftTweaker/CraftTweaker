package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class RecipeHandlerRegistrationHandler implements IRecipeHandlerRegistrationHandler {
    
    record HandlerData(Class<? extends Recipe<?>> recipeClass, IRecipeHandler<?> handler) {}
    
    private final Map<Class<? extends Recipe<?>>, HandlerData> handlers;
    
    private RecipeHandlerRegistrationHandler() {
        
        this.handlers = new HashMap<>();
    }
    
    static Collection<HandlerData> gather(final Consumer<IRecipeHandlerRegistrationHandler> consumer) {
        
        final RecipeHandlerRegistrationHandler handler = new RecipeHandlerRegistrationHandler();
        consumer.accept(handler);
        return Collections.unmodifiableCollection(handler.handlers.values());
    }
    
    @Override
    public <T extends Recipe<?>> void registerRecipeHandler(final Class<? extends T> recipe, final IRecipeHandler<T> handler) {
        
        // WTF javac?
        if(recipe == GenericUtil.uncheck(Recipe.class)) {
            
            throw new IllegalArgumentException("Unable to register a global handler for all recipes: attempted with " + handler);
        }
        
        final HandlerData data = this.handlers.get(recipe);
        if(data != null) {
            
            throw new IllegalArgumentException("A handler for the class '" + recipe.getName() + "' has already been registered: using " + data.handler() + " and not " + handler);
        }
        this.handlers.put(recipe, new HandlerData(recipe, handler));
    }
    
}
