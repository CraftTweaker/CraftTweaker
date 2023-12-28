package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/RandomChanceWithLootingLootCondition")
@NativeTypeRegistration(value = LootItemRandomChanceWithLootingCondition.class, zenCodeName = "crafttweaker.api.loot.condition.RandomChanceWithLootingLootCondition")
public final class ExpandLootItemRandomChanceWithLootingCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final float probability, final float lootingBoost) {
        
        return LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(probability, lootingBoost);
    }
    
}
