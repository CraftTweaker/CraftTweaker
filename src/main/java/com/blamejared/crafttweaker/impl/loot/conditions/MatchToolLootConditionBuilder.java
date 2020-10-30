package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.MatchTool")
@Document("vanilla/api/loot/conditions/MatchTool")
public final class MatchToolLootConditionBuilder implements ILootConditionTypeBuilder {
    // Two options:
    // Option 1: CrT
    private IItemStack tool;
    private boolean ignoreDamage;
    // Option 2: Vanilla
    //TODO("MCItemPredicate?")

    MatchToolLootConditionBuilder() {}

    // vanilla
    // TODO("Expose Vanilla")

    // CrT
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder withTool(final IItemStack tool) {
        this.tool = tool;
        return this;
    }

    @ZenCodeType.Method
    public MatchToolLootConditionBuilder ignoringDamage() {
        this.ignoreDamage = true;
        return this;
    }

    @Override
    public ILootCondition finish() {
        // TODO("Check that someone isn't using both CrT and vanilla methods")
        if (this.tool == null) {
            throw new IllegalStateException("A tool must be specified for a 'MatchTool' condition");
        }
        //noinspection ConstantConditions
        if (this.tool != null) {
            // crt
            return context -> context.getTool().matches(this.tool, this.ignoreDamage);
        } else {
            // TODO("Vanilla Condition")
            // vanilla
            return null;
        }
    }
}
