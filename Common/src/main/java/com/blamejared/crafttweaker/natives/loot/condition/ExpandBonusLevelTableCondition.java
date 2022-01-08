package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/BonusLevelTableLootCondition")
@NativeTypeRegistration(value = BonusLevelTableCondition.class, zenCodeName = "crafttweaker.api.loot.condition.BonusLevelTableLootCondition")
public final class ExpandBonusLevelTableCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final Enchantment enchantment, final float... values) {
        
        return BonusLevelTableCondition.bonusLevelFlatChance(enchantment, values);
    }
    
}
