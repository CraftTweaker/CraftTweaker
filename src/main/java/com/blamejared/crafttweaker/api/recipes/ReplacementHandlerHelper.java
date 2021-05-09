package com.blamejared.crafttweaker.api.recipes;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

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
    
    public static <T extends IRecipe<?>, U> Optional<Function<ResourceLocation, T>> replaceNonNullIngredientList(final NonNullList<U> originalIngredients, final Class<U> ingredientClass,
                                                                                                                 final List<IReplacementRule> rules,
                                                                                                                 final Function<NonNullList<U>, Function<ResourceLocation, T>> factory) {
        return replaceIngredientList(originalIngredients, ingredientClass, rules, list -> factory.apply(Util.make(NonNullList.create(), it -> it.addAll(list))));
    }
    
    public static <T extends IRecipe<?>, U> Optional<Function<ResourceLocation, T>> replaceIngredientList(final List<U> originalIngredients, final Class<U> ingredientClass,
                                                                              final List<IReplacementRule> rules, final Function<List<U>, Function<ResourceLocation, T>> factory) {
        final ReplacementMap<U> replacements = IntStream.range(0, originalIngredients.size())
                .mapToObj(i -> Pair.of(i, IRecipeHandler.attemptReplacing(originalIngredients.get(i), ingredientClass, rules)))
                .filter(it -> it.getSecond().isPresent())
                .collect(ReplacementMap::new, ReplacementMap::put, ReplacementMap::merge);
    
        if (replacements.isEmpty()) return Optional.empty();
    
        final List<U> newIngredients = new ArrayList<>(originalIngredients.size());
    
        replacements.fill(newIngredients::set);
    
        return Optional.of(factory.apply(newIngredients));
    }
    
    public static <T extends IRecipe<?>, U> Optional<Function<ResourceLocation, T>> replaceIngredientArray(final U[] originalIngredients, final Class<U> ingredientClass,
                                                                                                           final List<IReplacementRule> rules,
                                                                                                           final Function<U[], Function<ResourceLocation, T>> factory) {
        final ReplacementMap<U> replacements = IntStream.range(0, originalIngredients.length)
                .mapToObj(i -> Pair.of(i, IRecipeHandler.attemptReplacing(originalIngredients[i], ingredientClass, rules)))
                .filter(it -> it.getSecond().isPresent())
                .collect(ReplacementMap::new, ReplacementMap::put, ReplacementMap::merge);
        
        if (replacements.isEmpty()) return Optional.empty();
        
        final U[] newIngredients = Arrays.copyOf(originalIngredients, originalIngredients.length);
        
        replacements.fill((i, ing) -> newIngredients[i] = ing);
        
        return Optional.of(factory.apply(newIngredients));
    }
}
