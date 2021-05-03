package com.blamejared.crafttweaker.api.recipes;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public final class ReplacementHandlerHelper {
    private static final class ReplacementMap<T> {
        private final Int2ObjectMap<T> delegate;
        
        ReplacementMap() {
            this.delegate = new Int2ObjectRBTreeMap<>();
            this.delegate.defaultReturnValue(null);
        }
        
        T get(final int index, final List<T> original) {
            final T replaced = this.delegate.get(index);
            return replaced == null? original.get(index) : replaced;
        }
    
        T get(final int index, final T[] original) {
            final T replaced = this.delegate.get(index);
            return replaced == null? original[index] : replaced;
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
    
    public static <T extends IRecipe<?>, U> Optional<T> replaceNonNullIngredientList(final NonNullList<U> originalIngredients, final Class<U> ingredientClass,
                                                                                     final List<IReplacementRule> rules, final Function<NonNullList<U>, T> factory) {
        return replaceIngredientList(originalIngredients, ingredientClass, rules, list -> factory.apply(Util.make(NonNullList.create(), it -> it.addAll(list))));
    }
    
    public static <T extends IRecipe<?>, U> Optional<T> replaceIngredientList(final List<U> originalIngredients, final Class<U> ingredientClass,
                                                                              final List<IReplacementRule> rules, final Function<List<U>, T> factory) {
        final ReplacementMap<U> replacements = IntStream.range(0, originalIngredients.size())
                .mapToObj(i -> Pair.of(i, IRecipeHandler.attemptReplacing(originalIngredients.get(i), ingredientClass, rules)))
                .filter(it -> it.getSecond().isPresent())
                .collect(ReplacementMap::new, ReplacementMap::put, ReplacementMap::merge);
    
        if (replacements.isEmpty()) return Optional.empty();
    
        final List<U> newIngredients = Arrays.asList(arrayOf(ingredientClass, originalIngredients.size()));
    
        for (int i = 0, size = originalIngredients.size(); i < size; ++i) {
            newIngredients.set(i, replacements.get(i, originalIngredients));
        }
    
        return Optional.of(factory.apply(newIngredients));
    }
    
    public static <T extends IRecipe<?>, U> Optional<T> replaceIngredientArray(final U[] originalIngredients, final Class<U> ingredientClass,
                                                                               final List<IReplacementRule> rules, final Function<U[], T> factory) {
        final ReplacementMap<U> replacements = IntStream.range(0, originalIngredients.length)
                .mapToObj(i -> Pair.of(i, IRecipeHandler.attemptReplacing(originalIngredients[i], ingredientClass, rules)))
                .filter(it -> it.getSecond().isPresent())
                .collect(ReplacementMap::new, ReplacementMap::put, ReplacementMap::merge);
        
        if (replacements.isEmpty()) return Optional.empty();
        
        final U[] newIngredients = arrayOf(ingredientClass, originalIngredients.length);
        
        for (int i = 0, size = originalIngredients.length; i < size; ++i) {
            newIngredients[i] = replacements.get(i, originalIngredients);
        }
        
        return Optional.of(factory.apply(newIngredients));
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T[] arrayOf(final Class<T> componentType, final int length) {
        return (T[]) Array.newInstance(componentType, length);
    }
}
