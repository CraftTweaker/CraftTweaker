package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.TriState;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.WeatherCheck")
@Document("vanilla/api/loot/conditions/vanilla/WeatherCheck")
public final class WeatherCheckLootConditionTypeBuilder implements ILootConditionTypeBuilder {
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
        if (this.raining.isUnset() && this.thundering.isUnset()) {
            CraftTweakerAPI.logWarning("'WeatherCheck' loot condition has both raining and thundering unset: this is useless");
        }
        return context -> {
            final World world = ExpandLootContext.getWorld(context);
            if (!this.raining.isUnset() && !this.raining.match(world.isRaining())) return false;
            if (!this.thundering.isUnset()) return this.thundering.match(world.isThundering());
            return true;
        };
    }
}
