package com.blamejared.crafttweaker.impl.registry.recipe;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.crafting.Recipe;

import java.util.*;
import java.util.stream.Collectors;

public final class RecipeHandlerRegistry implements IRecipeHandlerRegistry {
    
    private static final class DefaultRecipeHandler implements IRecipeHandler<Recipe<?>> {
        
        private static final DefaultRecipeHandler INSTANCE = new DefaultRecipeHandler();
        
        @Override
        public String dumpToCommandString(IRecipeManager manager, Recipe<?> recipe) {
            
            
            return String.format("~~ Recipe name: %s, Outputs: %s, Inputs: [%s], Recipe Class: %s, Recipe Serializer: %s ~~", recipe.getId(), Services.PLATFORM.createMCItemStack(recipe.getResultItem())
                    .getCommandString(), recipe.getIngredients()
                    .stream()
                    .map(IIngredient::fromIngredient)
                    .map(IIngredient::getCommandString)
                    .collect(Collectors.joining(", ")), recipe.getClass()
                    .getName(), Services.REGISTRY.getRegistryKey(recipe.getSerializer()));
        }
        
    }
    
    private final Map<Class<? extends Recipe<?>>, IRecipeHandler<?>> recipeHandlers = new HashMap<>();
    
    public <T extends Recipe<?>> void register(Class<? extends T> clazz, IRecipeHandler<T> handler) {
        
        this.recipeHandlers.put(clazz, handler);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(final T recipe) {
        
        return (IRecipeHandler<T>) getRecipeHandlerFor(recipe.getClass());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(Class<T> recipeClass) {
        
        return (IRecipeHandler<T>) getRecipeHandlerForClass(recipeClass).orElse(DefaultRecipeHandler.INSTANCE);
    }
    
    private Optional<IRecipeHandler<?>> getRecipeHandlerForClass(final Class<?> recipeClass) {
        
        final Deque<Class<?>> classes = new ArrayDeque<>();
        classes.offer(recipeClass);
        
        while(!classes.isEmpty()) {
            final Class<?> target = classes.poll();
            
            // Don't check for Recipe: we don't allow any handlers with that superclass
            if(target == Recipe.class) {
                continue;
            }
            
            final IRecipeHandler<?> attempt = this.recipeHandlers.get(target);
            if(attempt != null) {
                return Optional.of(attempt);
            }
            
            if(target.getSuperclass() != null) {
                classes.offer(target.getSuperclass());
            }
            Arrays.stream(target.getInterfaces()).forEach(classes::offer);
        }
        
        return Optional.empty();
    }
    
}
