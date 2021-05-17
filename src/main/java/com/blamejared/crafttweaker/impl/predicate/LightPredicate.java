package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a predicate for the light level in an area.
 *
 * In the base game, the light level in an area can only go from 0 (inclusive) to 16 (exclusive), and is a whole number.
 * Nevertheless, the predicate does not perform any validity checks. Values that go outside these bounds will
 * automatically be clamped to the two limits.
 *
 * By default, any light level passes this check.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.LightPredicate")
@Document("vanilla/api/predicate/LightPredicate")
public final class LightPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.LightPredicate> {
    private IntRangePredicate range;

    public LightPredicate() {
        super(net.minecraft.advancements.criterion.LightPredicate.ANY);
        this.range = IntRangePredicate.unbounded();
    }

    /**
     * Sets the minimum value the light level should be to <code>min</code>.
     *
     * If the light level had already some bounds specified, then the minimum value of the bound will be overwritten
     * with the value specified in <code>min</code>. On the other hand, if the light level didn't have any bounds set,
     * the minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the light level should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LightPredicate withMinimumLightLevel(final int min) {
        this.range = IntRangePredicate.mergeLowerBound(this.range, min);
        return this;
    }

    /**
     * Sets the maximum value the light level should be to <code>max</code>.
     *
     * If the light level had already some bounds specified, then the maximum value of the bound will be overwritten
     * with the value specified in <code>max</code>. On the other hand, if the light level didn't have any bounds set,
     * the maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the light level should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LightPredicate withMaximumLightLevel(final int max) {
        this.range = IntRangePredicate.mergeUpperBound(this.range, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum value the light level should be to <code>min</code> and <code>max</code>
     * respectively.
     *
     * If the light level had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the light level should be.
     * @param max The maximum value the light level should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LightPredicate withBoundedLightLevel(final int min, final int max) {
        this.range = IntRangePredicate.bounded(min, max);
        return this;
    }

    @Override
    public boolean isAny() {
        return this.range.isAny();
    }

    @Override
    public net.minecraft.advancements.criterion.LightPredicate toVanilla() {
        return new net.minecraft.advancements.criterion.LightPredicate(this.range.toVanillaPredicate());
    }
}
