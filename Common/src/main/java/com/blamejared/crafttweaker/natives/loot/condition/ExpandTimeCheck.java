package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.predicates.TimeCheck;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/TimeCheckLootCondition")
@NativeTypeRegistration(value = TimeCheck.class, zenCodeName = "crafttweaker.api.loot.condition.TimeCheckLootCondition")
public final class ExpandTimeCheck {
    
    @ZenCodeType.StaticExpansionMethod
    public static TimeCheck.Builder create(final IntRange range) {
        
        return TimeCheck.time(range);
    }
    
}
