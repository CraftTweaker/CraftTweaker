package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.criterion.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

/**
 * Represents a predicate for a range of integer values.
 *
 * This predicate cannot be built directly and is only used within other predicates for bounding rules and checks. This
 * predicate matches including bounds, if present. It may also be unbounded on both sides, allowing effectively any
 * value, or only one side, acting as a lower or upper limit.
 *
 * A version of this predicate for decimals is also provided in the form of {@link FloatRangePredicate}.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.IntRangePredicate")
@Document("vanilla/api/predicate/IntRangePredicate")
public final class IntRangePredicate extends IVanillaWrappingPredicate.AnyDefaulting<MinMaxBounds.IntBound> {
    
    private static final IntRangePredicate UNBOUNDED = new IntRangePredicate(Integer.MIN_VALUE, Integer.MAX_VALUE);
    
    private final int min;
    private final int max;
    
    private IntRangePredicate(final int min, final int max) {
        
        super(MinMaxBounds.IntBound.UNBOUNDED);
        this.min = min;
        this.max = max;
    }
    
    public static IntRangePredicate unbounded() {
        
        return UNBOUNDED;
    }
    
    public static IntRangePredicate lowerBounded(final int min) {
        
        return new IntRangePredicate(min, Integer.MAX_VALUE);
    }
    
    public static IntRangePredicate upperBounded(final int max) {
        
        return new IntRangePredicate(Integer.MIN_VALUE, max);
    }
    
    public static IntRangePredicate bounded(final int min, final int max) {
    
        if(min > max) {
            throw new IllegalArgumentException("Minimum IntRange bound must not be more than maximum bound");
        }
        return new IntRangePredicate(min, max);
    }
    
    public static IntRangePredicate mergeLowerBound(final IntRangePredicate previous, final int min) {
    
        if(previous == null) {
            return lowerBounded(min);
        }
        return bounded(min, previous.getMax());
    }
    
    public static IntRangePredicate mergeUpperBound(final IntRangePredicate previous, final int max) {
    
        if(previous == null) {
            return upperBounded(max);
        }
        return bounded(previous.getMin(), max);
    }
    
    public int getMin() {
        
        return this.min;
    }
    
    public int getMax() {
        
        return this.max;
    }
    
    public boolean match(final int target) {
        
        return this.min <= target && target <= this.max;
    }
    
    @Override
    public boolean isAny() {
        
        return this == UNBOUNDED;
    }
    
    @Override
    public MinMaxBounds.IntBound toVanilla() {
        
        return new MinMaxBounds.IntBound(this.min, this.max);
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if(this == o) {
            return true;
        }
        if(o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final IntRangePredicate that = (IntRangePredicate) o;
        return this.getMin() == that.getMin() && this.getMax() == that.getMax();
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
