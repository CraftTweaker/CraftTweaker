package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

@ZenRegister
@Document("vanilla/api/loot/condition/LootCondition")
@NativeTypeRegistration(value = LootItemCondition.class, zenCodeName = "crafttweaker.api.loot.condition.LootCondition")
public final class ExpandLootItemCondition {
}
