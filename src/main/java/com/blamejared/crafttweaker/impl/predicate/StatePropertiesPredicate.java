package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;
import org.openzen.zencode.java.ZenCodeType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.StatePropertiesPredicate")
@Document("vanilla/api/predicate/StatePropertiesPredicate")
public final class StatePropertiesPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.StatePropertiesPredicate> {
    private static class RawMatcher {
        private final String minOrVal;
        private final String max;
        private final boolean range;
        private final BiPredicate<StateHolder<?, ?>, Property<?>> matcherFunction;

        private RawMatcher(final String min, final String max) {
            this.minOrVal = min;
            this.max = max;
            this.range = true;
            this.matcherFunction = (holder, property) -> rangedMatcher(holder, property, this.getMin(), this.getMax());
        }

        private RawMatcher(final String val) {
            this.minOrVal = val;
            this.max = null;
            this.range = false;
            this.matcherFunction = (holder, property) -> exactMatcher(holder, property, this.getVal());
        }

        private static <T extends Comparable<T>> boolean exactMatcher(final StateHolder<?, ?> holder, final Property<T> property, final String value) {
            final T propertyValue = holder.get(property);
            final Optional<T> targetValue = property.parseValue(value);
            return targetValue.isPresent() && propertyValue.compareTo(targetValue.get()) == 0;
        }

        private static <T extends Comparable<T>> boolean rangedMatcher(final StateHolder<?, ?> holder, final Property<T> property, final String min, final String max) {
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

        private BiPredicate<StateHolder<?, ?>, Property<?>> getMatcherFunction() {
            return this.matcherFunction;
        }
    }

    private final Map<String, RawMatcher> rawMatchers;

    public StatePropertiesPredicate() {
        super(net.minecraft.advancements.criterion.StatePropertiesPredicate.EMPTY);
        this.rawMatchers = new LinkedHashMap<>();
    }

    @ZenCodeType.Method
    public StatePropertiesPredicate withExactProperty(final String name, final String value) {
        this.rawMatchers.put(name, new RawMatcher(value));
        return this;
    }

    @ZenCodeType.Method
    public StatePropertiesPredicate withExactProperty(final String name, final int value) {
        return this.withExactProperty(name, Integer.toString(value));
    }

    @ZenCodeType.Method
    public StatePropertiesPredicate withExactProperty(final String name, final boolean value) {
        return this.withExactProperty(name, Boolean.toString(value));
    }

    @ZenCodeType.Method
    public StatePropertiesPredicate withRangedProperty(final String name, @ZenCodeType.Nullable final String min, @ZenCodeType.Nullable final String max) {
        this.rawMatchers.put(name, new RawMatcher(min, max));
        return this;
    }

    @ZenCodeType.Method
    public StatePropertiesPredicate withRangedProperty(final String name, final int min, final int max) {
        return this.withRangedProperty(name, Integer.toString(min), Integer.toString(max));
    }

    @ZenCodeType.Method
    public StatePropertiesPredicate withUpperBoundedProperty(final String name, final int max) {
        return this.withRangedProperty(name, null, Integer.toString(max));
    }

    @ZenCodeType.Method
    public StatePropertiesPredicate withLowerBoundedProperty(final String name, final int min) {
        return this.withRangedProperty(name, Integer.toString(min), null);
    }

    public <S extends StateHolder<?, S>> boolean matchProperties(final StateContainer<?, S> state, final S blockState) {
        return this.rawMatchers.entrySet().stream().allMatch(entry -> {
            final Property<?> targetProperty = state.getProperty(entry.getKey());
            return targetProperty != null && entry.getValue().getMatcherFunction().test(blockState, targetProperty);
        });
    }

    @Override
    public boolean isAny() {
        return this.rawMatchers.isEmpty();
    }

    @Override
    public net.minecraft.advancements.criterion.StatePropertiesPredicate toVanilla() {
        final List<net.minecraft.advancements.criterion.StatePropertiesPredicate.Matcher> vanillaMatchers =
                this.rawMatchers.entrySet().stream().map(this::toVanilla).collect(Collectors.toList());
        return new net.minecraft.advancements.criterion.StatePropertiesPredicate(vanillaMatchers);
    }

    private net.minecraft.advancements.criterion.StatePropertiesPredicate.Matcher toVanilla(final Map.Entry<String, RawMatcher> rawEntry) {
        final RawMatcher matcher = rawEntry.getValue();
        if (matcher.isRange()) {
            return new net.minecraft.advancements.criterion.StatePropertiesPredicate.RangedMacher(rawEntry.getKey(), matcher.getMin(), matcher.getMax());
        } else {
            return new net.minecraft.advancements.criterion.StatePropertiesPredicate.ExactMatcher(rawEntry.getKey(), matcher.getVal());
        }
    }
}
