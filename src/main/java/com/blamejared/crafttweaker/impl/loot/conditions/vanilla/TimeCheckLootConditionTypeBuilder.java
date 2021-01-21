package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.IntRangePredicate;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.TimeCheck")
@Document("vanilla/api/loot/conditions/vanilla/TimeCheck")
public final class TimeCheckLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private int timePeriod;
    private IntRangePredicate value;

    TimeCheckLootConditionTypeBuilder() {
        this.timePeriod = 0;
        this.value = IntRangePredicate.unlimited();
    }

    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withTimePeriod(final int period) {
        this.timePeriod = period;
        return this;
    }

    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withMinimumValue(final int min) {
        this.value = IntRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withMaximumValue(final int max) {
        this.value = IntRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withRangedValue(final int min, final int max) {
        this.value = IntRangePredicate.bounded(min, max);
        return this;
    }

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
