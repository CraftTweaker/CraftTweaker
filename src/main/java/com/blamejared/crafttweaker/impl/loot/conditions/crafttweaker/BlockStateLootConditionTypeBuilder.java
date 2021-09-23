package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.BlockState;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder to create a 'BlockState' loot condition.
 *
 * The condition compares the block state obtained from the {@link net.minecraft.loot.LootContext} with the
 * given one, passing only if they are exactly the same state.
 *
 * A 'BlockState' loot condition requires a block state to be built.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.BlockState")
@Document("vanilla/api/loot/conditions/crafttweaker/BlockState")
public final class BlockStateLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    private BlockState state;
    
    BlockStateLootConditionTypeBuilder() {}
    
    /**
     * Sets the state that the condition must check.
     *
     * The state will be matched exactly.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param state The state to check.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public BlockStateLootConditionTypeBuilder withState(final BlockState state) {
        
        this.state = state;
        return this;
    }
    
    @Override
    public ILootCondition finish() {
        
        if(this.state == null) {
            throw new IllegalStateException("A block state for a 'BlockState' condition must be specified");
        }
        return context -> {
            final BlockState state = ExpandLootContext.getBlockState(context);
            if(state == null) {
                return false;
            }
            return state.equals(this.state);
        };
    }
    
}
