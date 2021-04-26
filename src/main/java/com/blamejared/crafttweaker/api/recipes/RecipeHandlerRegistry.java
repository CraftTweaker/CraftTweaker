package com.blamejared.crafttweaker.api.recipes;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RecipeHandlerRegistry {
    private static final class DefaultRecipeHandler implements IRecipeHandler<IRecipe<?>> {
        private static final DefaultRecipeHandler INSTANCE = new DefaultRecipeHandler();
        
        @Override
        public String dumpToCommandString(final IRecipeManager manager, final IRecipe<?> recipe) {
            
            return String.format(
                    "~~ Recipe name: %s, Outputs: %s, Inputs: [%s], Recipe Class: %s, Recipe Serializer: %s ~~",
                    recipe.getId(),
                    new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString(),
                    recipe.getIngredients().stream().map(IIngredient::fromIngredient).map(IIngredient::getCommandString).collect(Collectors.joining(", ")),
                    recipe.getClass().getName(),
                    recipe.getSerializer().getRegistryName()
            );
        }
    }
    
    private final Map<Class<? extends IRecipe<?>>, IRecipeHandler<?>> recipeHandlers = new HashMap<>();
    
    public void addClass(final Class<?> clazz) {
        if (IRecipeHandler.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " does not implement IRecipeHandler");
        }
        if (clazz.isInterface()) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is an interface and cannot be annotated with @IRecipeHandler.For");
        }
        Arrays.stream(clazz.getAnnotationsByType(IRecipeHandler.For.class))
                .map(IRecipeHandler.For::value)
                .filter(it -> it != IRecipe.class)
                .forEach(it -> {
                    if (recipeHandlers.containsKey(it)) {
                        CraftTweakerAPI.logWarning(
                                "Multiple recipe handlers found for the same recipe class %s: attempted registration of %s, using %s",
                                it.getName(),
                                clazz.getName(),
                                recipeHandlers.get(it).getClass().getName()
                        );
                    } else {
                        recipeHandlers.put(it, (IRecipeHandler<?>) InstantiationUtil.getOrCreateInstance(clazz));
                    }
                });
    }
    
    @SuppressWarnings("unchecked")
    public <T extends IRecipe<?>> IRecipeHandler<T> getHandlerFor(final T recipe) {
        return (IRecipeHandler<T>) this.getHandlerFor(recipe.getClass()).orElse(DefaultRecipeHandler.INSTANCE);
    }
    
    private Optional<IRecipeHandler<?>> getHandlerFor(final Class<?> recipeClass) {
        final Deque<Class<?>> classes = new ArrayDeque<>();
        classes.offer(recipeClass);
        
        while (!classes.isEmpty()) {
            final Class<?> target = classes.poll();
            
            // Don't check for IRecipe: we don't allow any handlers with that superclass
            if (target == IRecipe.class) continue;
            
            final IRecipeHandler<?> attempt = recipeHandlers.get(target);
            if (attempt != null) {
                return Optional.of(attempt);
            }
            
            classes.offer(target.getSuperclass());
            Arrays.stream(target.getInterfaces()).forEach(classes::offer);
        }
        
        return Optional.empty();
    }
}
