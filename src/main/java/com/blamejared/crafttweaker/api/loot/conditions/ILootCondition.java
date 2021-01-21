package com.blamejared.crafttweaker.api.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.ILootCondition")
@Document("vanilla/api/loot/conditions/ILootCondition")
public interface ILootCondition {
    @ZenCodeType.Method
    boolean test(final LootContext context);
}
