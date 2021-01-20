package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

final class StateBasedPredicateHelper {
    private static class Raw {
        private final String minOrVal;
        private final String max;
        private final boolean range;

        private Raw(final String min, final String max) {
            this.minOrVal = min;
            this.max = max;
            this.range = true;
        }

        private Raw(final String val) {
            this.minOrVal = val;
            this.max = null;
            this.range = false;
        }

        private boolean isRange() {
            return this.range;
        }

        private String getVal() {
            if (this.isRange()) throw new IllegalStateException("Not an exact raw entry");
            return this.minOrVal;
        }

        private String getMin() {
            if (!this.isRange()) throw new IllegalStateException("Not a ranged raw entry");
            return this.minOrVal;
        }

        private String getMax() {
            if (!this.isRange()) throw new IllegalStateException("Not a ranged raw entry");
            return this.max;
        }
    }

    private final Map<String, Raw> rawMatchers;
    private final Map<String, BiPredicate<StateHolder<?, ?>, Property<?>>> matchers;

    StateBasedPredicateHelper() {
        this.rawMatchers = new LinkedHashMap<>();
        this.matchers = new LinkedHashMap<>();
    }

    boolean isAny() {
        return this.matchers.isEmpty();
    }

    void addExactProperty(final String name, final String value) {
        this.rawMatchers.put(name, new Raw(value));
        this.matchers.put(name, (holder, property) -> this.exactMatcher(holder, property, value));
    }

    void addRangedProperty(final String name, final String min, final String max) {
        this.rawMatchers.put(name, new Raw(min, max));
        this.matchers.put(name, (holder, property) -> (min != null || max != null) && this.rangedMatcher(holder, property, min, max));
    }

    boolean canMatch() {
        return !this.matchers.isEmpty();
    }

    StatePropertiesPredicate toVanilla() {
        if (this.isAny()) return StatePropertiesPredicate.EMPTY;
        final List<StatePropertiesPredicate.Matcher> vanillaMatchers = this.rawMatchers.entrySet().stream().map(this::toVanilla).collect(Collectors.toList());
        try {
            return new StatePropertiesPredicate(vanillaMatchers);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private StatePropertiesPredicate.Matcher toVanilla(final Map.Entry<String, Raw> rawEntry) {
        // Note: using 'invoke' instead of 'invokeExact' due to the lack of being able to specify return type
        // This is slightly slower, but it should still be faster:tm: than pure reflection
        try {
            if (rawEntry.getValue().isRange()) {
                return new StatePropertiesPredicate.RangedMacher(rawEntry.getKey(), rawEntry.getValue().getMin(), rawEntry.getValue().getMax());
            } else {
                return new StatePropertiesPredicate.ExactMatcher(rawEntry.getKey(), rawEntry.getValue().getVal());
            }
        } catch (final Throwable t) {
            throw new RuntimeException(t);
        }
    }

    <S extends StateHolder<?, S>> boolean matchProperties(final StateContainer<?, S> state, final S blockState) {
        return this.matchers.entrySet().stream().allMatch(entry -> {
            final Property<?> targetProperty = state.getProperty(entry.getKey());
            return targetProperty != null && entry.getValue().test(blockState, targetProperty);
        });
    }

    private <T extends Comparable<T>> boolean exactMatcher(final StateHolder<?, ?> holder, final Property<T> property, final String value) {
        final T propertyValue = holder.get(property);
        final Optional<T> targetValue = property.parseValue(value);
        return targetValue.isPresent() && propertyValue.compareTo(targetValue.get()) == 0;
    }

    private <T extends Comparable<T>> boolean rangedMatcher(final StateHolder<?, ?> holder, final Property<T> property, final String min, final String max) {
        final T propertyValue = holder.get(property);

        if (min != null) {
            final Optional<T> parsedMin = property.parseValue(min);
            if (!parsedMin.isPresent() || propertyValue.compareTo(parsedMin.get()) < 0) {
                return false;
            }
        }

        if (max != null) {
            final Optional<T> parsedMax = property.parseValue(max);
            return parsedMax.isPresent() && propertyValue.compareTo(parsedMax.get()) <= 0;
        }

        return true;
    }
}
