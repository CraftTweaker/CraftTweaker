package com.blamejared.crafttweaker.api.recipe.handler;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.crafting.Recipe;

import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RecipeHandlerRegistry {
    
    private static final class DefaultRecipeHandler implements IRecipeHandler<Recipe<?>> {
        
        private static final DefaultRecipeHandler INSTANCE = new DefaultRecipeHandler();
    
        @Override
        public String dumpToCommandString(IRecipeManager manager, Recipe<?> recipe) {
    
    
            return String.format(
                    "~~ Recipe name: %s, Outputs: %s, Inputs: [%s], Recipe Class: %s, Recipe Serializer: %s ~~",
                    recipe.getId(),
                    Services.PLATFORM.createMCItemStack(recipe.getResultItem())
                            .getCommandString(),
                    recipe.getIngredients()
                            .stream()
                            .map(IIngredient::fromIngredient)
                            .map(IIngredient::getCommandString)
                            .collect(Collectors.joining(", ")),
                    recipe.getClass().getName(),
                    Services.REGISTRY.getRegistryKey(recipe.getSerializer())
            );
        }
    
    }
    
    private final Map<Class<? extends Recipe<?>>, IRecipeHandler<?>> recipeHandlers = new HashMap<>();
    
    public void addClass(final Class<?> clazz) {
        
        if(!IRecipeHandler.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " does not implement IRecipeHandler");
        }
        if(clazz.isInterface()) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is an interface and cannot be annotated with @IRecipeHandler.For");
        }
        if(Modifier.isAbstract(clazz.getModifiers())) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is an abstract class and cannot be annotated with @IRecipeHandler.For");
        }
        //noinspection RedundantCast
        Arrays.stream(clazz.getAnnotationsByType(IRecipeHandler.For.class))
                .map(IRecipeHandler.For::value)
                .filter(it -> (Class<?>) it != Recipe.class)
                .forEach(it -> {
                    if(this.recipeHandlers.containsKey(it)) {
                        CraftTweakerAPI.LOGGER.warn(
                                "Multiple recipe handlers found for the same recipe class {}: attempted registration of {}, using {}",
                                it.getName(),
                                clazz.getName(),
                                this.recipeHandlers.get(it).getClass().getName()
                        );
                    } else {
                        this.recipeHandlers.put(it, (IRecipeHandler<?>) InstantiationUtil.getOrCreateInstance(clazz));
                    }
                });
    }
    
    public <T extends Recipe<?>> IRecipeHandler<T> getHandlerFor(final T recipe) {
        
        return (IRecipeHandler<T>) this.getHandlerFor(recipe.getClass())
                .orElse(DefaultRecipeHandler.INSTANCE);
    }
    
    private Optional<IRecipeHandler<?>> getHandlerFor(final Class<?> recipeClass) {
        
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
