package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.criterion.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

/**
 * Represents a predicate for a range of floating point (decimal) values.
 *
 * This predicate cannot be built directly and is only used within other predicates for bounding rules and checks. This
 * predicate matches including bounds, if present. It may also be unbounded on both sides, allowing effectively any
 * value, or only one side, acting as a lower or upper limit.
 *
 * A version of this predicate for integers is also provided in the form of {@link IntRangePredicate}.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.FloatRangePredicate")
@Document("vanilla/api/predicate/FloatRangePredicate")
public final class FloatRangePredicate extends IVanillaWrappingPredicate.AnyDefaulting<MinMaxBounds.FloatBound> {
    private static final FloatRangePredicate UNBOUNDED = new FloatRangePredicate(-Float.MAX_VALUE, Float.MAX_VALUE);

    private final float min;
    private final float max;

    private FloatRangePredicate(final float min, final float max) {
        super(MinMaxBounds.FloatBound.UNBOUNDED);
        this.min = min;
        this.max = max;
    }

    public static FloatRangePredicate unbounded() {
        return UNBOUNDED;
    }

    public static FloatRangePredicate lowerBounded(final float min) {
        return new FloatRangePredicate(min, Float.MAX_VALUE);
    }

    public static FloatRangePredicate upperBounded(final float max) {
        return new FloatRangePredicate(-Float.MAX_VALUE, max);
    }

    public static FloatRangePredicate bounded(final float min, final float max) {
        if (min < max) throw new IllegalArgumentException("Minimum FloatRange bound must not be less than maximum bound");
        return new FloatRangePredicate(min, max);
    }
    
    public static FloatRangePredicate mergeLowerBound(final FloatRangePredicate previous, final float min) {
        if (previous == null) return lowerBounded(min);
        return bounded(min, previous.getMax());
    }
    
    public static FloatRangePredicate mergeUpperBound(final FloatRangePredicate previous, final float max) {
        if (previous == null) return upperBounded(max);
        return bounded(previous.getMin(), max);
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }

    public boolean match(final int target) {
        return this.min <= target && target <= this.max;
    }

    @Override
    public boolean isAny() {
        return this.equals(UNBOUNDED);
    }

    @Override
    public MinMaxBounds.FloatBound toVanilla() {
        return new MinMaxBounds.FloatBound(this.min, this.max);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final FloatRangePredicate that = (FloatRangePredicate) o;
        return Float.compare(that.getMin(), this.getMin()) == 0 && Float.compare(that.getMax(), this.getMax()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getMin(), this.getMax());
    }

    @Override
    public String toString() {
        return this.getMin() + ".." + this.getMax();
    }
}
