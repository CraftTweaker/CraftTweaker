package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/loot/condition/builder/LootTableIdLootConditionBuilder")
@NativeTypeRegistration(value = LootTableIdCondition.Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.LootTableIdLootConditionBuilder")
public final class ExpandLootTableIdConditionBuilder {
    
    // TODO workaround for ZC JFITI issues
    @ZenCodeType.Caster(implicit = true)
    public static LootItemCondition asSupplier(LootTableIdCondition.Builder internal) {
        
        return internal.build();
    }
}
