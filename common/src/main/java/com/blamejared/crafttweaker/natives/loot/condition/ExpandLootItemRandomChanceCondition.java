package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/RandomChanceLootCondition")
@NativeTypeRegistration(value = LootItemRandomChanceCondition.class, zenCodeName = "crafttweaker.api.loot.condition.RandomChanceLootCondition")
public final class ExpandLootItemRandomChanceCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final float probability) {
        
        return LootItemRandomChanceCondition.randomChance(probability);
    }
    
}
