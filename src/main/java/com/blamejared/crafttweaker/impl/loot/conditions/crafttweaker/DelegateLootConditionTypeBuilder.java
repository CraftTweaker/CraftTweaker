package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.Delegate")
@Document("vanilla/api/loot/conditions/crafttweaker/Delegate")
public final class DelegateLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private ILootCondition delegate;

    DelegateLootConditionTypeBuilder() {}

    @ZenCodeType.Method
    public DelegateLootConditionTypeBuilder withDelegate(final ILootCondition other) {
        this.delegate = other;
        return this;
    }

    @ZenCodeType.Method
    public DelegateLootConditionTypeBuilder withDelegate(final CTLootConditionBuilder builder) {
        return this.withDelegate(builder.single());
    }

    @Override
    public ILootCondition finish() {
        if (this.delegate == null) {
            throw new IllegalStateException("You must specify a delegate function for a 'Delegate' loot condition");
        }
        return this.delegate;
    }
}
