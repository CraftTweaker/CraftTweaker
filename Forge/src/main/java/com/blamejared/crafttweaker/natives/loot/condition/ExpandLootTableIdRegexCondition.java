package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.LootTableIdRegexCondition;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/LootTableIdRegexLootCondition")
@NativeTypeRegistration(value = LootTableIdRegexCondition.class, zenCodeName = "crafttweaker.api.loot.condition.LootTableIdRegexLootCondition")
public final class ExpandLootTableIdRegexCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final String pattern) {
        
        return LootTableIdRegexCondition.builder(pattern);
    }
    
}
