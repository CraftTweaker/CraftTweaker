package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.TimeCheck;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/TimeCheckLootConditionBuilder")
@NativeTypeRegistration(value = TimeCheck.Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.TimeCheckLootConditionBuilder")
public final class ExpandTimeCheckBuilder {
    
    @ZenCodeType.Method
    public static TimeCheck.Builder period(final TimeCheck.Builder internal, final long period) {
        
        return internal.setPeriod(period);
    }
    
}
