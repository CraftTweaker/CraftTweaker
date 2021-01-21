package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.Reference")
@Document("vanilla/api/loot/conditions/vanilla/Reference")
public final class ReferenceLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private ResourceLocation predicateName;

    ReferenceLootConditionTypeBuilder() {}

    @ZenCodeType.Method
    public ReferenceLootConditionTypeBuilder withName(final ResourceLocation name) {
        this.predicateName = name;
        return this;
    }

    @ZenCodeType.Method
    public ReferenceLootConditionTypeBuilder withName(final String name) {
        return this.withName(new ResourceLocation(name));
    }

    @Override
    public ILootCondition finish() {
        if (this.predicateName == null) {
            throw new IllegalStateException("You must specify the predicate name that you want to be able to query in a 'Reference' loot condition");
        }
        return context -> {
            final net.minecraft.loot.conditions.ILootCondition vanillaCondition = context.getLootCondition(this.predicateName);
            if (context.addCondition(vanillaCondition)) {
                try {
                    return vanillaCondition.test(context);
                } finally {
                    context.removeCondition(vanillaCondition);
                }
            } else {
                CraftTweakerAPI.logError("Prevented infinite loop from ensuing with predicate '{}' referenced by a 'Reference' loot condition", this.predicateName);
                return false;
            }
        };
    }
}
