package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/WeatherCheckLootCondition")
@NativeTypeRegistration(value = WeatherCheck.class, zenCodeName = "crafttweaker.api.loot.condition.WeatherCheckLootCondition")
public final class ExpandWeatherCheck {
    
    @ZenCodeType.StaticExpansionMethod
    public static WeatherCheck.Builder create() {
        
        return WeatherCheck.weather();
    }
    
}
