package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.world.MCWorld;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.WeatherCheck")
@Document("vanilla/api/loot/conditions/WeatherCheck")
public final class WeatherCheckLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private enum TriState {
        TRUE,
        FALSE,
        UNSET
    }

    private TriState raining;
    private TriState thundering;

    WeatherCheckLootConditionTypeBuilder() {
        this.raining = TriState.UNSET;
        this.thundering = TriState.UNSET;
    }

    @ZenCodeType.Method
    public WeatherCheckLootConditionTypeBuilder withRain() {
        this.raining = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public WeatherCheckLootConditionTypeBuilder withoutRain() {
        this.raining = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public WeatherCheckLootConditionTypeBuilder withThunders() {
        this.thundering = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public WeatherCheckLootConditionTypeBuilder withoutThunders() {
        this.thundering = TriState.FALSE;
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.raining == TriState.UNSET && this.thundering == TriState.UNSET) {
            throw new IllegalStateException("At least one between raining and thundering must be set to a value, but they're both unset");
        }
        return context -> {
            final MCWorld world = context.getWorld();
            if (this.raining != TriState.UNSET) {
                final boolean rain = this.raining == TriState.TRUE;
                if (rain != world.isRaining()) return false;
            }
            if (this.thundering != TriState.UNSET) {
                final boolean storm = this.thundering == TriState.TRUE;
                return storm == world.isThundering();
            }
            return true;
        };
    }
}
