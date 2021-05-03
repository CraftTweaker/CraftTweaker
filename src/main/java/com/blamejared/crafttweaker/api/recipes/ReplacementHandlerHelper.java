package com.blamejared.crafttweaker.api.recipes;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

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
        
        T get(final int index, final NonNullList<T> original) {
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
    
    public static <T extends IRecipe<?>> Optional<T> replaceIngredients(final NonNullList<Ingredient> originalIngredients, final List<IReplacementRule> rules,
                                                                        final Function<NonNullList<Ingredient>, T> factory) {
        final ReplacementMap<Ingredient> replacements = IntStream.range(0, originalIngredients.size())
                .mapToObj(i -> Pair.of(i, IRecipeHandler.attemptReplacing(originalIngredients.get(i), Ingredient.class, rules)))
                .filter(it -> it.getSecond().isPresent())
                .collect(ReplacementMap::new, ReplacementMap::put, ReplacementMap::merge);
    
        if (replacements.isEmpty()) return Optional.empty();
    
        final NonNullList<Ingredient> newIngredients = NonNullList.withSize(originalIngredients.size(), Ingredient.EMPTY);
    
        for (int i = 0, size = originalIngredients.size(); i < size; ++i) {
            newIngredients.set(i, replacements.get(i, originalIngredients));
        }
    
        return Optional.of(factory.apply(newIngredients));
    }
    
    public static <T extends IRecipe<?>> Optional<T> replaceIIngredients(final IIngredient[] originalIngredients, final List<IReplacementRule> rules,
                                                                         final Function<IIngredient[], T> factory) {
        final ReplacementMap<IIngredient> replacements = IntStream.range(0, originalIngredients.length)
                .mapToObj(i -> Pair.of(i, IRecipeHandler.attemptReplacing(originalIngredients[i], IIngredient.class, rules)))
                .filter(it -> it.getSecond().isPresent())
                .collect(ReplacementMap::new, ReplacementMap::put, ReplacementMap::merge);
        
        if (replacements.isEmpty()) return Optional.empty();
        
        final IIngredient[] newIngredients = new IIngredient[originalIngredients.length];
        
        for (int i = 0, size = originalIngredients.length; i < size; ++i) {
            newIngredients[i] = replacements.get(i, originalIngredients);
        }
        
        return Optional.of(factory.apply(newIngredients));
    }
}
