package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/KilledByPlayerLootCondition")
@NativeTypeRegistration(value = LootItemKilledByPlayerCondition.class, zenCodeName = "crafttweaker.api.loot.condition.KilledByPlayerLootCondition")
public final class ExpandLootItemKilledByPlayerCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create() {
        
        return LootItemKilledByPlayerCondition.killedByPlayer();
    }
    
}
