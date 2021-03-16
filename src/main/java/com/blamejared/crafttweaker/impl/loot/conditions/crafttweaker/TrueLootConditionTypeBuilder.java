package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.True")
@Document("vanilla/api/loot/conditions/crafttweaker/True")
public final class TrueLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    static final TrueLootConditionTypeBuilder INSTANCE = new TrueLootConditionTypeBuilder();
    private static final ILootCondition TRUE = context -> true;
    
    private TrueLootConditionTypeBuilder() {}
    
    @Override
    public ILootCondition finish() {
        return TRUE;
    }
}
