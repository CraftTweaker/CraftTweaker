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

/**
 * Builder to create a 'BlockStateProperty' loot condition.
 *
 * This condition compares the the block state obtained from the {@link net.minecraft.loot.LootContext} and attempts to
 * match it to the given {@link Block}. If this comparison succeeds, then the state is further compared according to the
 * rules outlined in the {@link StatePropertiesPredicate}.
 *
 * This condition thus passes only if the block matches the given one and, optionally, if all the state properties match
 * according to the predicate given to this loot condition.
 *
 * A 'BlockStateProperty' condition requires a block to be correctly built. Properties may or may not be specified.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.BlockStateProperty")
@Document("vanilla/api/loot/conditions/vanilla/BlockStateProperty")
public final class BlockStatePropertyLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    private Block block;
    private StatePropertiesPredicate predicate;
    
    BlockStatePropertyLootConditionTypeBuilder() {
        
        this.predicate = new StatePropertiesPredicate();
    }
    
    /**
     * Sets the block that should be matched by the loot condition.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param block The block to be matched.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withBlock(final Block block) {
        
        this.block = block;
        return this;
    }
    
    /**
     * Creates and sets the {@link StatePropertiesPredicate} that will be matched against the state's properties.
     *
     * Any changes that have already been made to the predicate will be overwritten, effectively replacing the previous
     * predicate, if any.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param builder A consumer that will be used to configure the {@link StatePropertiesPredicate}.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withStatePropertiesPredicate(final Consumer<StatePropertiesPredicate> builder) {
        
        final StatePropertiesPredicate predicate = new StatePropertiesPredicate();
        builder.accept(predicate);
        this.predicate = predicate;
        return this;
    }
    
    @Override
    public ILootCondition finish() {
        
        if(this.block == null) {
            throw new IllegalStateException("'BlockStateProperty' condition requires a block to be specified");
        }
        return context -> {
            final BlockState state = ExpandLootContext.getBlockState(context);
            return state != null && state.getBlock() == this.block && this.predicate.matchProperties(state.getBlock()
                    .getStateContainer(), state);
        };
    }
    
}
