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

/**
 * Represents a predicate for a {@link net.minecraft.block.Block} or {@link net.minecraft.fluid.Fluid} state properties.
 *
 * This predicate can check an arbitrary amount of properties either for an exact match or a value that is within the
 * range of allowed values. The predicate is <strong>not</strong> able to check for the absence of a state property, and
 * requires all specified properties to be present on the targeted state.
 *
 * By default, no properties are checked, effectively allowing any block or fluid state.
 */
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

    /**
     * Adds the property <code>name</code> to the list of properties that should be matched, along with a string value
     * that should be matched exactly.
     *
     * If the same property had already been specified, then the previous value is replaced, no matter the resulting
     * type (i.e. replacing an integer with a string is allowed).
     *
     * @param name The name of the property to check.
     * @param value The string value the property must have.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public StatePropertiesPredicate withExactProperty(final String name, final String value) {
        this.propertyMatchers.put(name, new ExactPropertyMatcher(value));
        return this;
    }

    /**
     * Adds the property <code>name</code> to the list of properties that should be matched, along with an integer value
     * that should be matched exactly.
     *
     * If the same property had already been specified, then the previous value is replaced, no matter the resulting
     * type (i.e. replacing a string with an integer is allowed).
     *
     * @param name The name of the property to check.
     * @param value The integer value the property must have.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public StatePropertiesPredicate withExactProperty(final String name, final int value) {
        return this.withExactProperty(name, Integer.toString(value));
    }

    /**
     * Adds the property <code>name</code> to the list of properties that should be matched, along with a boolean value
     * that should be matched exactly.
     *
     * If the same property had already been specified, then the previous value is replaced, no matter the resulting
     * type (i.e. replacing an integer with a boolean is allowed).
     *
     * @param name The name of the property to check.
     * @param value The boolean value the property must have.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public StatePropertiesPredicate withExactProperty(final String name, final boolean value) {
        return this.withExactProperty(name, Boolean.toString(value));
    }

    /**
     * Adds the property <code>name</code> to the list of properties that should be matched, along with a range that
     * goes from <code>min</code> to <code>max</code> in which the string value should be.
     *
     * If the same property had already been specified, then the previous value is replaced, no matter the resulting
     * type (i.e. replacing an integer with a string is allowed).
     *
     * A null value in either <code>min</code> or <code>max</code> is considered as an unbounded limit, effectively
     * making the interval open on one side.
     *
     * A null value in both <code>min</code> and <code>max</code> is treated as a simple presence check, without
     * caring about the actual value of the property.
     *
     * @param name The name of the property to check.
     * @param min The minimum string value the property must have, or null to indicate no minimum value.
     * @param max The maximum string value the property must have, or null to indicate no maximum value.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public StatePropertiesPredicate withRangedProperty(final String name, @ZenCodeType.Nullable final String min, @ZenCodeType.Nullable final String max) {
        this.propertyMatchers.put(name, new RangedPropertyMatcher(min, max));
        return this;
    }

    /**
     * Adds the property <code>name</code> to the list of properties that should be matched, along with a range that
     * goes from <code>min</code> to <code>max</code> in which the integer value should be.
     *
     * If the same property had already been specified, then the previous value is replaced, no matter the resulting
     * type (i.e. replacing a string with an integer is allowed).
     *
     * @param name The name of the property to check.
     * @param min The minimum integer value the property must have.
     * @param max The maximum integer value the property must have.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public StatePropertiesPredicate withRangedProperty(final String name, final int min, final int max) {
        return this.withRangedProperty(name, Integer.toString(min), Integer.toString(max));
    }

    /**
     * Adds the property <code>name</code> to the list of properties that should be matched, along with an upper limit
     * on the integer values the property can assume, set to <code>max</code>.
     *
     * If the same property had already been specified, then the previous value is replaced, no matter the resulting
     * type (i.e. replacing a string with an integer is allowed).
     *
     * @param name The name of the property to check.
     * @param max The maximum integer value the property must have.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public StatePropertiesPredicate withUpperBoundedProperty(final String name, final int max) {
        return this.withRangedProperty(name, null, Integer.toString(max));
    }

    /**
     * Adds the property <code>name</code> to the list of properties that should be matched, along with a lower limit
     * on the integer values the property can assume, set to <code>min</code>.
     *
     * If the same property had already been specified, then the previous value is replaced, no matter the resulting
     * type (i.e. replacing a string with an integer is allowed).
     *
     * @param name The name of the property to check.
     * @param min The minimum integer value the property must have.
     * @return This predicate for chaining.
     */
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
