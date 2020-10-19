package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.MCLootCondition")
@Document("vanilla/api/loot/MCLootCondition")
@ZenWrapper(wrappedClass = "net.minecraft.loot.conditions.ILootCondition")
public class MCLootCondition implements ILootCondition {
    private final net.minecraft.loot.conditions.ILootCondition wrapped;

    public MCLootCondition(final net.minecraft.loot.conditions.ILootCondition wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean test(MCLootContext context) {
        return this.wrapped.test(context.getInternal());
    }

    public net.minecraft.loot.conditions.ILootCondition getInternal() {
        return this.wrapped;
    }
}
