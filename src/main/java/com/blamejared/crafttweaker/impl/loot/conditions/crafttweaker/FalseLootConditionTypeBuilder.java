package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder to create a 'False' loot condition.
 *
 * A 'False' loot condition is guaranteed to always fail.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.False")
@Document("vanilla/api/loot/conditions/crafttweaker/False")
public final class FalseLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    static final FalseLootConditionTypeBuilder INSTANCE = new FalseLootConditionTypeBuilder();
    private static final ILootCondition FALSE = context -> false;
    
    private FalseLootConditionTypeBuilder() {}
    
    @Override
    public ILootCondition finish() {
        
        return FALSE;
    }
    
}
