package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.condition.MCLootCondition")
@Document("vanilla/api/loot/condition/MCLootCondition")
public final class MCLootCondition implements ILootCondition {
    private final net.minecraft.loot.conditions.ILootCondition wrapped;

    public MCLootCondition(final net.minecraft.loot.conditions.ILootCondition wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean test(LootContext context) {
        return this.wrapped.test(context);
    }

    public net.minecraft.loot.conditions.ILootCondition getInternal() {
        return this.wrapped;
    }
}
