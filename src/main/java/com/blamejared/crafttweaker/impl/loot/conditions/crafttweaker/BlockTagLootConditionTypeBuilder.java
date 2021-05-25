package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder to create a 'BlockTag' loot condition.
 *
 * <p>The condition passes only if the block obtained from the {@link net.minecraft.loot.LootContext} is contained from
 * within the given tag.</p>
 *
 * <p>A 'BlockTag' loot condition requires a block tag to be built.</p>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.BlockTag")
@Document("vanilla/api/loot/conditions/crafttweaker/BlockTag")
public final class BlockTagLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private MCTag<Block> targetTag;
    
    BlockTagLootConditionTypeBuilder() {}
    
    /**
     * Sets the {@link MCTag} that the condition must check.
     *
     * <p>This parameter is <strong>required</strong>.</p>
     *
     * @param tag The tag to check.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public BlockTagLootConditionTypeBuilder withTag(final MCTag<Block> tag) {
        this.targetTag = tag;
        return this;
    }
    
    @Override
    public ILootCondition finish() {
        if (this.targetTag == null) {
            throw new IllegalStateException("A tag for a 'BlockTag' condition must be specified");
        }
        return context -> {
            final BlockState state = ExpandLootContext.getBlockState(context);
            
            return state != null && state.getBlock().isIn(CraftTweakerHelper.getITag(this.targetTag));
        };
    }
    
}
