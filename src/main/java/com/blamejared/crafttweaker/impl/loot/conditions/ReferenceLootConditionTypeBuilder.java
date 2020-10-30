package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.util.MCResourceLocation;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.Reference")
@Document("vanilla/api/loot/conditions/Reference")
public final class ReferenceLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    // vanilla
    private MCResourceLocation predicateName;
    // crt
    private ILootCondition referenced;

    ReferenceLootConditionTypeBuilder() {}

    @ZenCodeType.Method
    public ReferenceLootConditionTypeBuilder withName(final MCResourceLocation name) {
        this.predicateName = name;
        return this;
    }

    @ZenCodeType.Method
    public ReferenceLootConditionTypeBuilder withName(final String name) {
        return this.withName(new MCResourceLocation(new ResourceLocation(name)));
    }

    @ZenCodeType.Method
    public ReferenceLootConditionTypeBuilder withReference(final ILootCondition other) {
        this.referenced = other;
        return this;
    }

    @ZenCodeType.Method
    public ReferenceLootConditionTypeBuilder withReference(final CTLootConditionBuilder builder) {
        return this.withReference(builder.single());
    }

    @Override
    public ILootCondition finish() {
        if (this.predicateName == null && this.referenced == null) {
            throw new IllegalStateException("Either the predicate name or the referenced loot condition must be set");
        }
        if (this.predicateName != null && this.referenced != null) {
            throw new IllegalStateException("Unable to set both the referenced loot condition and the predicate name: only one can be set at a time");
        }
        if (this.predicateName != null) {
            // vanilla
            return context -> {
                final net.minecraft.loot.conditions.ILootCondition vanillaCondition = context.getInternal().getLootCondition(this.predicateName.getInternal());
                if (context.getInternal().addCondition(vanillaCondition)) {
                    try {
                        return vanillaCondition.test(context.getInternal());
                    } finally {
                        context.getInternal().removeCondition(vanillaCondition);
                    }
                } else {
                    CraftTweakerAPI.logError("Prevented infinite loop from ensuing with predicate '{}' referenced by a 'Reference' loot condition", this.predicateName);
                    return false;
                }
            };
        } else {
            // crt
            return this.referenced;
        }
    }
}
