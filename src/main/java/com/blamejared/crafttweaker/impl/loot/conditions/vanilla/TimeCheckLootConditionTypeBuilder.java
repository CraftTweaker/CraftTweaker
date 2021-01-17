package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.TimeCheck")
@Document("vanilla/api/loot/conditions/vanilla/TimeCheck")
public final class TimeCheckLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private boolean hasPeriod;
    private long timePeriod;
    private boolean hasRange;
    private long min;
    private long max;

    TimeCheckLootConditionTypeBuilder() {}

    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withTimePeriod(final long period) {
        this.hasPeriod = true;
        this.timePeriod = period;
        return this;
    }

    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withRange(final long min, final long max) {
        this.hasRange = true;
        this.min = min;
        this.max = max;
        return this;
    }

    @ZenCodeType.Method
    public TimeCheckLootConditionTypeBuilder withValue(final long value) {
        return this.withRange(value, value);
    }

    @Override
    public ILootCondition finish() {
        if (!this.hasRange) {
            throw new IllegalStateException("Unable to build a time condition that doesn't have bounds!");
        }
        if (this.min > this.max) {
            CraftTweakerAPI.logWarning("A 'TimeCheck' condition has a minimum value that is bigger than the maximum (min: {}, max: {}): it will never pass!", this.min, this.max);
        }
        return context -> {
            final World world = ExpandLootContext.getWorld(context);
            final long time = world.getDayTime();
            final long actualTime = this.hasPeriod? time % this.timePeriod : time;
            return this.min <= actualTime && actualTime <= this.max;
        };
    }
}
