package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder for a 'LootTableId' loot condition.
 *
 * This condition will test the ID of the loot table currently being queried, as specified by the
 * {@link net.minecraft.loot.LootContext}. The ID is matched exactly, without any form of leeway in terms of regular
 * expressions or prefixes.
 *
 * While this condition is provided merely as a convenience, it is suggested not to rely on this condition only. Due to
 * backwards compatibility, some loot tables may in fact lack an ID or have overlapping ones. Other conditions should be
 * used instead.
 *
 * The 'LootTableId' condition requires an ID to be built.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.LootTableId")
@Document("vanilla/api/loot/conditions/vanilla/LootTableId")
public final class LootTableIdLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private ResourceLocation tableId;

    LootTableIdLootConditionTypeBuilder() {}

    /**
     * Sets the ID of the loot table that should be targeted.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param location The ID of the loot table to match, in {@link ResourceLocation} form.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public LootTableIdLootConditionTypeBuilder withTableId(final ResourceLocation location) {
        this.tableId = location;
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.tableId == null) {
            throw new IllegalStateException("Unable to have a 'LootTableId' condition without an ID");
        }
        return context -> this.tableId.equals(ExpandLootContext.getLootTableId(context));
    }
}
