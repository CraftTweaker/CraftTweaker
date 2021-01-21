package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.StatePropertiesPredicate;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.BlockStateProperty")
@Document("vanilla/api/loot/conditions/vanilla/BlockStateProperty")
public final class BlockStatePropertyLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private Block block;
    private StatePropertiesPredicate predicate;

    BlockStatePropertyLootConditionTypeBuilder() {
        this.predicate = new StatePropertiesPredicate();
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withBlock(final Block block) {
        this.block = block;
        return this;
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withStatePropertiesPredicate(final Consumer<StatePropertiesPredicate> builder) {
        final StatePropertiesPredicate predicate = new StatePropertiesPredicate();
        builder.accept(predicate);
        this.predicate = predicate;
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.block == null) {
            throw new IllegalStateException("'BlockStateProperty' condition requires a block to be specified");
        }
        return context -> {
            final BlockState state = ExpandLootContext.getBlockState(context);
            return state != null && state.getBlock() == this.block && this.predicate.matchProperties(state.getBlock().getStateContainer(), state);
        };
    }
}
