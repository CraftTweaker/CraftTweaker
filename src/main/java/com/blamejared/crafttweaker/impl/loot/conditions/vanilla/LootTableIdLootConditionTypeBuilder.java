package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.LootTableId")
@Document("vanilla/api/loot/conditions/vanilla/LootTableId")
public final class LootTableIdLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private ResourceLocation tableId;

    LootTableIdLootConditionTypeBuilder() {}

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
