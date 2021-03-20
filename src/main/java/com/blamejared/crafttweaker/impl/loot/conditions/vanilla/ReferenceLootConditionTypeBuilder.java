package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder for a 'Reference' loot condition.
 *
 * A Reference condition defers checking to another loot condition, identified by the given {@link ResourceLocation},
 * that acts as the name of the additional condition. This allows additional conditions that may have been created in
 * a data-pack to be referred by other conditions, created from within scripts.
 *
 * This condition acts as the vanilla counterpart of the
 * {@link com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.DelegateLootConditionTypeBuilder} loot
 * condition, providing support only for data-packs instead of scripts.
 *
 * A 'Reference' loot condition requires the name of the condition to call to be built correctly.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.Reference")
@Document("vanilla/api/loot/conditions/vanilla/Reference")
public final class ReferenceLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private ResourceLocation predicateName;

    ReferenceLootConditionTypeBuilder() {}

    /**
     * Sets the name of the data-pack loot condition to query.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param name The name of the predicate to query.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public ReferenceLootConditionTypeBuilder withName(final ResourceLocation name) {
        this.predicateName = name;
        return this;
    }

    /**
     * Sets the name of the data-pack loot condition to query.
     *
     * The name is treated as a {@link ResourceLocation}, lacking the type safety of the bracket handler. For this
     * reason, it's suggested to prefer the method with a {@link ResourceLocation} as parameter.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param name The name of the predicate to query, in {@link ResourceLocation} form.
     * @return This builder for chaining.
     */
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
                CraftTweakerAPI.logError("Prevented infinite loop from ensuing with predicate '%s' referenced by a 'Reference' loot condition", this.predicateName);
                return false;
            }
        };
    }
}
