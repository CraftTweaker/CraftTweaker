package com.blamejared.crafttweaker.api.recipe.handler.helper;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Set of helper functions that can replace ingredients according to the given {@link IReplacementRule}s.
 *
 * @see IReplacementRule
 * @see IRecipeHandler#attemptReplacing(Object, Class, Recipe, List)
 */
public final class ReplacementHandlerHelper {
    
    private static final class ReplacementMap<T> {
        
        private interface ReplacementConsumer<T> {
            
            void accept(int index, T ingredient);
            
        }
        
        private final Int2ObjectMap<T> delegate;
        
        ReplacementMap() {
            
            this.delegate = new Int2ObjectRBTreeMap<>();
            this.delegate.defaultReturnValue(null);
        }
        
        void fill(final ReplacementConsumer<T> consumer) {
            
            this.delegate.int2ObjectEntrySet().forEach(it -> consumer.accept(it.getIntKey(), it.getValue()));
        }
        
        void put(final int index, final T ingredient) {
            
            this.delegate.put(index, ingredient);
        }
        
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        void put(final Pair<Integer, Optional<T>> pair) {
            
            this.put(pair.getFirst(), pair.getSecond().get());
        }
        
        void merge(final ReplacementMap<T> other) {
            
            this.delegate.putAll(other.delegate);
        }
        
        boolean isEmpty() {
            
            return this.delegate.isEmpty();
        }
        
    }
    
    private ReplacementHandlerHelper() {}
    
    /**
     * Replaces the given {@link NonNullList} of ingredients of type {@code ingredientClass} according to the given set
     * of rules.
     *
     * <p>This acts as the {@code NonNullList} equivalent of
     * {@link #replaceIngredientList(List, Class, Recipe, List, Function)}, in case the recipe constructor explicitly
     * requires a {@code NonNullList} to be passed as a parameter.</p>
     *
     * @param originalIngredients The original {@link NonNullList} of ingredients that will be replaced; this
     *                            <strong>should</strong> match the list of ingredients in the recipe.
     * @param ingredientClass     The type of the ingredient that should be replaced. Its value may or may not correspond to
     *                            the actual ingredient's class, although it must be one of its superclass (in other words,
     *                            it is not necessary for {@code ingredientClass == originalIngredients[i].getClass()} for
     *                            any {@code i} between {@code 0} and {@code originalIngredients.size()}; on the other hand,
     *                            {@code ingredientClass.isAssignableFrom(originalIngredients[i].getClass())} must hold true
     *                            for every {@code i} in the same range).
     * @param recipe              The recipe whose ingredients are currently undergoing replacement.
     * @param rules               The list of {@link IReplacementRule}s that need to be applied to the recipe.
     * @param factory             A {@link Function} that accepts a {@link NonNullList} of ingredients of type {@code U} as a first
     *                            parameter and returns a {@link Function} that can create the recipe given an ID in the form of
     *                            a {@link ResourceLocation} (it effectively is a factory for a recipe factory).
     * @param <T>                 The type of the recipe whose ingredients are currently undergoing replacement.
     * @param <U>                 The type of the ingredient that is currently being replaced. No restrictions are placed on the type
     *                            of the ingredient.
     *
     * @return An {@link Optional} holding a {@link Function} that is able to create a new recipe with the replaced
     * ingredient list when given an ID in the form of a {@link ResourceLocation}, if any replacements have been carried
     * out on one or more of its ingredients; {@link Optional#empty()} otherwise.
     */
    public static <T extends Recipe<?>, U> Optional<Function<ResourceLocation, T>> replaceNonNullIngredientList(final NonNullList<U> originalIngredients, final Class<U> ingredientClass,
                                                                                                                final T recipe, final List<IReplacementRule> rules,
                                                                                                                final Function<NonNullList<U>, Function<ResourceLocation, T>> factory) {
        
        return replaceIngredientList(originalIngredients, ingredientClass, recipe, rules, list -> factory.apply(Util.make(NonNullList.create(), it -> it.addAll(list))));
    }
    
    /**
     * Replaces the given {@link List} of ingredients of type {@code ingredientClass} according to the given set of
     * rules.
     *
     * <p>If the recipe requires a {@link NonNullList} as a parameter, it is possible to use the more specialized
     * version {@link #replaceNonNullIngredientList(NonNullList, Class, Recipe, List, Function)}.</p>
     *
     * @param originalIngredients The original {@link List} of ingredients that will be replaced; this
     *                            <strong>should</strong> match the list of ingredients in the recipe.
     * @param ingredientClass     The type of the ingredient that should be replaced. Its value may or may not correspond to
     *                            the actual ingredient's class, although it must be one of its superclass (in other words,
     *                            it is not necessary for {@code ingredientClass == originalIngredients[i].getClass()} for
     *                            any {@code i} between {@code 0} and {@code originalIngredients.size()}; on the other hand,
     *                            {@code ingredientClass.isAssignableFrom(originalIngredients[i].getClass())} must hold true
     *                            for every {@code i} in the same range).
     * @param recipe              The recipe whose ingredients are currently undergoing replacement.
     * @param rules               The list of {@link IReplacementRule}s that need to be applied to the recipe.
     * @param factory             A {@link Function} that accepts a {@link List} of ingredients of type {@code U} as a first
     *                            parameter and returns a {@link Function} that can create the recipe given an ID in the form of
     *                            a {@link ResourceLocation} (it effectively is a factory for a recipe factory).
     * @param <T>                 The type of the recipe whose ingredients are currently undergoing replacement.
     * @param <U>                 The type of the ingredient that is currently being replaced. No restrictions are placed on the type
     *                            of the ingredient.
     *
     * @return An {@link Optional} holding a {@link Function} that is able to create a new recipe with the replaced
     * ingredient list when given an ID in the form of a {@link ResourceLocation}, if any replacements have been carried
     * out on one or more of its ingredients; {@link Optional#empty()} otherwise.
     */
    public static <T extends Recipe<?>, U> Optional<Function<ResourceLocation, T>> replaceIngredientList(final List<U> originalIngredients, final Class<U> ingredientClass,
                                                                                                         final T recipe, final List<IReplacementRule> rules,
                                                                                                         final Function<List<U>, Function<ResourceLocation, T>> factory) {
        
        final ReplacementMap<U> replacements = IntStream.range(0, originalIngredients.size())
                .mapToObj(i -> Pair.of(i, IRecipeHandler.attemptReplacing(originalIngredients.get(i), ingredientClass, recipe, rules)))
                .filter(it -> it.getSecond().isPresent())
                .collect(ReplacementMap::new, ReplacementMap::put, ReplacementMap::merge);
        
        if(replacements.isEmpty()) {
            return Optional.empty();
        }
        
        final List<U> newIngredients = new ArrayList<>(originalIngredients);
        
        replacements.fill(newIngredients::set);
        
        return Optional.of(factory.apply(newIngredients));
    }
    
    /**
     * Replaces the given array of ingredients of type {@code ingredientClass} according to the given set of rules.
     *
     * @param originalIngredients The original array of ingredients that will be replaced; this <strong>should</strong>
     *                            match the array of ingredients in the recipe.
     * @param ingredientClass     The type of the ingredient that should be replaced. Its value may or may not correspond to
     *                            the actual ingredient's class, although it must be one of its superclass (in other words,
     *                            it is not necessary for {@code ingredientClass == originalIngredients[i].getClass()} for
     *                            any {@code i} between {@code 0} and {@code originalIngredients.size()}; on the other hand,
     *                            {@code ingredientClass.isAssignableFrom(originalIngredients[i].getClass())} must hold true
     *                            for every {@code i} in the same range).
     * @param recipe              The recipe whose ingredients are currently undergoing replacement.
     * @param rules               The list of {@link IReplacementRule}s that need to be applied to the recipe.
     * @param factory             A {@link Function} that accepts an array of ingredients of type {@code U} as a first parameter and
     *                            returns a {@link Function} that can create the recipe given an ID in the form of a
     *                            {@link ResourceLocation} (it effectively is a factory for a recipe factory).
     * @param <T>                 The type of the recipe whose ingredients are currently undergoing replacement.
     * @param <U>                 The type of the ingredient that is currently being replaced. No restrictions are placed on the type
     *                            of the ingredient.
     *
     * @return An {@link Optional} holding a {@link Function} that is able to create a new recipe with the replaced
     * ingredient list when given an ID in the form of a {@link ResourceLocation}, if any replacements have been carried
     * out on one or more of its ingredients; {@link Optional#empty()} otherwise.
     */
    public static <T extends Recipe<?>, U> Optional<Function<ResourceLocation, T>> replaceIngredientArray(final U[] originalIngredients, final Class<U> ingredientClass,
                                                                                                          final T recipe, final List<IReplacementRule> rules,
                                                                                                          final Function<U[], Function<ResourceLocation, T>> factory) {
        
        final ReplacementMap<U> replacements = IntStream.range(0, originalIngredients.length)
                .mapToObj(i -> Pair.of(i, IRecipeHandler.attemptReplacing(originalIngredients[i], ingredientClass, recipe, rules)))
                .filter(it -> it.getSecond().isPresent())
                .collect(ReplacementMap::new, ReplacementMap::put, ReplacementMap::merge);
        
        if(replacements.isEmpty()) {
            return Optional.empty();
        }
        
        final U[] newIngredients = Arrays.copyOf(originalIngredients, originalIngredients.length);
        
        replacements.fill((i, ing) -> newIngredients[i] = ing);
        
        return Optional.of(factory.apply(newIngredients));
    }
    
}
