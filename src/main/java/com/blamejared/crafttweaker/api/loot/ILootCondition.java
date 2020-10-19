package com.blamejared.crafttweaker.api.loot;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.MCLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.ILootCondition")
@Document("vanilla/api/loot/ILootCondition")
public interface ILootCondition {
    @ZenCodeType.Method
    boolean test(final MCLootContext context);
}
