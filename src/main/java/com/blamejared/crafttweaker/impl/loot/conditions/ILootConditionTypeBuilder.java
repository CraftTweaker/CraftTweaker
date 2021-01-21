package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.ILootConditionTypeBuilder")
@Document("vanilla/api/loot/conditions/ILootConditionTypeBuilder")
public interface ILootConditionTypeBuilder {
    @ZenCodeType.Method
    ILootCondition finish();
}
