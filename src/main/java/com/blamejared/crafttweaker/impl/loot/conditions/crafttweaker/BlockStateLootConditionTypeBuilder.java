package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.BlockState;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.BlockState")
@Document("vanilla/api/loot/conditions/crafttweaker/BlockState")
public final class BlockStateLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private BlockState state;

    BlockStateLootConditionTypeBuilder() {}

    @ZenCodeType.Method
    public BlockStateLootConditionTypeBuilder withState(final BlockState state) {
        this.state = state;
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.state == null) {
            throw new IllegalStateException("A block state for a 'BlockState' condition must be specified");
        }
        return context -> Objects.requireNonNull(ExpandLootContext.getBlockState(context)).equals(this.state);
    }
}
