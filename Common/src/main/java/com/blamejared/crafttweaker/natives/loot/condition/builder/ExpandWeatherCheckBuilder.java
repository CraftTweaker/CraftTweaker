package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/WeatherCheckLootConditionBuilder")
@NativeTypeRegistration(value = WeatherCheck.Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.WeatherCheckLootConditionBuilder")
public final class ExpandWeatherCheckBuilder {
    
    @ZenCodeType.Method
    public static WeatherCheck.Builder raining(final WeatherCheck.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean raining) {
        
        return internal.setRaining(raining);
    }
    
    @ZenCodeType.Method
    public static WeatherCheck.Builder thundering(final WeatherCheck.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean thundering) {
        
        return internal.setThundering(thundering);
    }
    
    // TODO workaround for ZC JFITI issues
    @ZenCodeType.Caster(implicit = true)
    public static LootItemCondition asSupplier(WeatherCheck.Builder internal) {
        
        return internal.build();
    }
    
}
