package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.BlockPredicate")
@Document("vanilla/api/predicate/BlockPredicate")
public final class BlockPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.BlockPredicate> {
    private Block block;
    private MCTag<Block> blockTag;
    private NBTPredicate data;
    private StatePropertiesPredicate states;

    public BlockPredicate() {
        super(net.minecraft.advancements.criterion.BlockPredicate.ANY);
        this.data = new NBTPredicate();
        this.states = new StatePropertiesPredicate();
    }

    @ZenCodeType.Method
    public BlockPredicate withBlock(final Block block) {
        this.block = block;
        return this;
    }

    @ZenCodeType.Method
    public BlockPredicate withBlockTag(final MCTag<Block> blockTag) {
        this.blockTag = blockTag;
        return this;
    }

    @ZenCodeType.Method
    public BlockPredicate withDataPredicate(final Consumer<NBTPredicate> builder) {
        final NBTPredicate predicate = new NBTPredicate();
        builder.accept(predicate);
        this.data = predicate;
        return this;
    }

    @ZenCodeType.Method
    public BlockPredicate withStatePropertiesPredicate(final Consumer<StatePropertiesPredicate> builder) {
        final StatePropertiesPredicate predicate = new StatePropertiesPredicate();
        builder.accept(predicate);
        this.states = predicate;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.block == null && this.blockTag == null && this.data.isAny() && this.states.isAny();
    }

    @Override
    public net.minecraft.advancements.criterion.BlockPredicate toVanilla() {
        if (this.blockTag != null && this.block != null) {
            CraftTweakerAPI.logWarning("'BlockPredicate' specifies both a block and a tag: the second will take precedence");
        }
        return new net.minecraft.advancements.criterion.BlockPredicate(
                this.blockTag != null? CraftTweakerHelper.getITag(this.blockTag) : null,
                this.block,
                this.states.toVanillaPredicate(),
                this.data.toVanillaPredicate()
        );
    }

    public boolean matches(final LootContext context) {
        final BlockState state = ExpandLootContext.getBlockState(context);
        return state.getBlock() == this.block && this.states.matchProperties(state.getBlock().getStateContainer(), state);
    }
}
