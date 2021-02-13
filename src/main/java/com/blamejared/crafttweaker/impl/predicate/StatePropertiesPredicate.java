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
import java.util.function.Function;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.StatePropertiesPredicate")
@Document("vanilla/api/predicate/StatePropertiesPredicate")
public final class StatePropertiesPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.StatePropertiesPredicate> {
    private interface PropertyMatcher {
        BiPredicate<StateHolder<?, ?>, Property<?>> getMatcherFunction();
        Function<String, net.minecraft.advancements.criterion.StatePropertiesPredicate.Matcher> toVanilla();
    }
    
    private final static class ExactPropertyMatcher implements PropertyMatcher {
        private final String value;
        private final BiPredicate<StateHolder<?, ?>, Property<?>> matcher;
        
        private ExactPropertyMatcher(final String value) {
            this.value = value;
            this.matcher = (holder, property) -> match(holder, property, this.value);
        }
    
        @Override
        public BiPredicate<StateHolder<?, ?>, Property<?>> getMatcherFunction() {
            return this.matcher;
        }
    
        @Override
        public Function<String, net.minecraft.advancements.criterion.StatePropertiesPredicate.Matcher> toVanilla() {
            return name -> new net.minecraft.advancements.criterion.StatePropertiesPredicate.ExactMatcher(name, this.value);
        }
    
        private static <T extends Comparable<T>> boolean match(final StateHolder<?, ?> holder, final Property<T> property, final String value) {
            final T propertyValue = holder.get(property);
            final Optional<T> targetValue = property.parseValue(value);
            return targetValue.isPresent() && propertyValue.compareTo(targetValue.get()) == 0;
        }
    }
    
    private final static class RangedPropertyMatcher implements PropertyMatcher {
        private final String min;
        private final String max;
        private final BiPredicate<StateHolder<?, ?>, Property<?>> matcher;
        
        private RangedPropertyMatcher(final String min, final String max) {
            this.min = min;
            this.max = max;
            this.matcher = (holder, property) -> match(holder, property, this.min, this.max);
        }
    
        @Override
        public BiPredicate<StateHolder<?, ?>, Property<?>> getMatcherFunction() {
            return this.matcher;
        }
    
        @Override
        public Function<String, net.minecraft.advancements.criterion.StatePropertiesPredicate.Matcher> toVanilla() {
            return name -> new net.minecraft.advancements.criterion.StatePropertiesPredicate.RangedMacher(name, this.min, this.max);
        }
    
        private static <T extends Comparable<T>> boolean match(final StateHolder<?, ?> holder, final Property<T> property, final String min, final String max) {
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

    private final Map<String, PropertyMatcher> propertyMatchers;

    public StatePropertiesPredicate() {
        super(net.minecraft.advancements.criterion.StatePropertiesPredicate.EMPTY);
        this.propertyMatchers = new LinkedHashMap<>();
    }

    @ZenCodeType.Method
    public StatePropertiesPredicate withExactProperty(final String name, final String value) {
        this.propertyMatchers.put(name, new ExactPropertyMatcher(value));
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
        this.propertyMatchers.put(name, new RangedPropertyMatcher(min, max));
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
        return this.propertyMatchers.entrySet().stream().allMatch(entry -> {
            final Property<?> targetProperty = state.getProperty(entry.getKey());
            return targetProperty != null && entry.getValue().getMatcherFunction().test(blockState, targetProperty);
        });
    }

    @Override
    public boolean isAny() {
        return this.propertyMatchers.isEmpty();
    }

    @Override
    public net.minecraft.advancements.criterion.StatePropertiesPredicate toVanilla() {
        final List<net.minecraft.advancements.criterion.StatePropertiesPredicate.Matcher> vanillaMatchers =
                this.propertyMatchers.entrySet().stream().map(this::toVanilla).collect(Collectors.toList());
        return new net.minecraft.advancements.criterion.StatePropertiesPredicate(vanillaMatchers);
    }

    private net.minecraft.advancements.criterion.StatePropertiesPredicate.Matcher toVanilla(final Map.Entry<String, PropertyMatcher> rawEntry) {
        return rawEntry.getValue().toVanilla().apply(rawEntry.getKey());
    }
}
