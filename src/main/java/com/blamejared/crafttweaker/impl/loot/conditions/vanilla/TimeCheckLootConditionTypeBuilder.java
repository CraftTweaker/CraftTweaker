package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.IntRangePredicate;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder for the 'TimeCheck' loot condition.
 *
 * This condition checks the current game time, counted from day 0, making sure that it fits within some defined
 * boundaries. The checked time may undergo a modulo operation if desired, to ensure that the time period doesn't
 * increase infinitely or to restrict the game time to a specific portion of the world time.
 *
 * A 'TimeCheck' condition requires at least a minimum or a maximum bound for the world time. Moreover, the time period
 * must not be negative, if such a value gets specified.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.TimeCheck")
@Document("vanilla/api/loot/conditions/vanilla/TimeCheck")
public final class TimeCheckLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private int timePeriod;
    private IntRangePredicate value;

    TimeCheckLootConditionTypeBuilder() {
        this.timePeriod = 0;
        this.value = IntRangePredicate.unbounded();
    }

    /**
     * Sets the time period to use for the modulo operation.
     *
     * This effectively restricts the value of the time to check between 0 (inclusive) and <code>period</code>
     * (exclusive), making it particularly useful to track elements such as days or weeks. As an example, the value to
     * give <code>period</code> to track day time is 24000.
     *
     * A value of 0 disables the operation from being carried out. On the other hand a negative value is forbidden.
     *
     * @param period The time period to use for the modulo operation.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withTimePeriod(final int period) {
        this.timePeriod = period;
        return this;
    }

    /**
     * Sets the minimum value of the game time to <code>min</code>.
     *
     * If the game time had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the new value specified in <code>min</code>. On the other hand, if the game time didn't have any specified
     * bounds, the minimum value is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param min The minimum value the game time can have.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withMinimumValue(final int min) {
        this.value = IntRangePredicate.mergeLowerBound(this.value, min);
        return this;
    }

    /**
     * Sets the maximum value of the game time to <code>max</code>.
     *
     * If the game time had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the new value specified in <code>min</code>. On the other hand, if the game time didn't have any specified
     * bounds, the maximum value is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param max The maximum value the game time can have.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withMaximumValue(final int max) {
        this.value = IntRangePredicate.mergeUpperBound(this.value, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum values of the game time respectively to <code>min</code> and <code>max</code>.
     *
     * If the game time had already some bounds specified, then the bounds will get completely overwritten with the new
     * values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param min The minimum value the game time can have.
     * @param max The maximum value the game time can have.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withRangedValue(final int min, final int max) {
        this.value = IntRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the game time to exactly match the given <code>value</code>.
     *
     * If the game time had already some bounds specified, then the bounds will get overwritten.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param value The exact value the world time must have.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withExactValue(final int value) {
        return this.withRangedValue(value, value);
    }

    @Override
    public ILootCondition finish() {
        if (this.value.isAny()) {
            throw new IllegalStateException("A 'TimeCheck' condition needs a time range");
        }
        if (this.timePeriod < 0) {
            throw new IllegalStateException("A 'TimeCheck' condition needs a positive period");
        }
        return context -> {
            final World world = ExpandLootContext.getWorld(context);
            final long time = world.getDayTime();
            final int actualTime = (int) (this.timePeriod > 0? time % this.timePeriod : time);
            return this.value.match(actualTime);
        };
    }
}
