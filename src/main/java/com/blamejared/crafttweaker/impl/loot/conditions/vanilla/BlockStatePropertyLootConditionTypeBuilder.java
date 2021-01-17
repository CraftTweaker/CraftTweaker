package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.predicate.BlockPredicate;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.BlockStateProperty")
@Document("vanilla/api/loot/conditions/vanilla/BlockStateProperty")
public final class BlockStatePropertyLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private final BlockPredicate predicate;

    BlockStatePropertyLootConditionTypeBuilder() {
        this.predicate = new BlockPredicate();
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withBlock(final Block block) {
        this.predicate.withBlock(block);
        return this;
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withProperty(final String name, final String value) {
        this.predicate.withProperty(name, value);
        return this;
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withProperty(final String name, final int value) {
        return this.withProperty(name, Integer.toString(value));
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withProperty(final String name, final boolean value) {
        return this.withProperty(name, Boolean.toString(value));
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withRangedProperty(final String name, @ZenCodeType.Nullable final String min, @ZenCodeType.Nullable final String max) {
        this.predicate.withRangedProperty(name, min, max);
        return this;
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withRangedProperty(final String name, final int min, final int max) {
        return this.withRangedProperty(name, Integer.toString(min), Integer.toString(max));
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withUpperBoundedProperty(final String name, final int max) {
        return this.withRangedProperty(name, null, Integer.toString(max));
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withLowerBoundedProperty(final String name, final int min) {
        return this.withRangedProperty(name, Integer.toString(min), null);
    }

    @Override
    public ILootCondition finish() {
        if (!this.predicate.canMatch()) {
            throw new IllegalStateException("Required options are missing: block with properties must be specified");
        }
        return this.predicate::matches;
    }
}
