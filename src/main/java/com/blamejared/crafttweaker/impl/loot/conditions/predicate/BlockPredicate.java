package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.loot.LootContext;
import net.minecraft.tags.ITag;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.BlockPredicate")
@Document("vanilla/api/loot/conditions/predicate/BlockPredicate")
public final class BlockPredicate {
    private final StateBasedPredicateHelper helper;
    private Block block;
    private MCTag<Block> blockTag;
    private IData data;

    public BlockPredicate() {
        this.helper = new StateBasedPredicateHelper();
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
    public BlockPredicate withData(final IData data) {
        this.data = data;
        return this;
    }

    @ZenCodeType.Method
    public BlockPredicate withProperty(final String name, final String value) {
        this.helper.addExactProperty(name, value);
        return this;
    }

    @ZenCodeType.Method
    public BlockPredicate withProperty(final String name, final int value) {
        return this.withProperty(name, Integer.toString(value));
    }

    @ZenCodeType.Method
    public BlockPredicate withProperty(final String name, final boolean value) {
        return this.withProperty(name, Boolean.toString(value));
    }

    @ZenCodeType.Method
    public BlockPredicate withRangedProperty(final String name, @ZenCodeType.Nullable final String min, @ZenCodeType.Nullable final String max) {
        this.helper.addRangedProperty(name, min, max);
        return this;
    }

    @ZenCodeType.Method
    public BlockPredicate withRangedProperty(final String name, final int min, final int max) {
        return this.withRangedProperty(name, Integer.toString(min), Integer.toString(max));
    }

    @ZenCodeType.Method
    public BlockPredicate withUpperBoundedProperty(final String name, final int max) {
        return this.withRangedProperty(name, null, Integer.toString(max));
    }

    @ZenCodeType.Method
    public BlockPredicate withLowerBoundedProperty(final String name, final int min) {
        return this.withRangedProperty(name, Integer.toString(min), null);
    }

    boolean isAny() {
        return this.block == null && this.blockTag == null && this.data == null && this.helper.isAny();
    }

    public net.minecraft.advancements.criterion.BlockPredicate toVanilla() {
        if (this.data != null && !(this.data instanceof MapData)) {
            throw new IllegalStateException("Data specified in an 'EntityPredicate' must be a map");
        }
        if (this.isAny()) return net.minecraft.advancements.criterion.BlockPredicate.ANY;
        return new net.minecraft.advancements.criterion.BlockPredicate(
                this.toVanilla(this.blockTag),
                this.block,
                this.helper.toVanilla(),
                this.toVanilla(this.data)
        );
    }

    public boolean canMatch() {
        return this.block != null && this.helper.canMatch();
    }

    public boolean matches(final LootContext context) {
        final BlockState state = ExpandLootContext.getBlockState(context);
        return state.getBlock() == this.block && this.helper.matchProperties(state.getBlock().getStateContainer(), state);
    }

    private NBTPredicate toVanilla(final IData data) {
        if (data == null) return NBTPredicate.ANY;
        return new NBTPredicate(((MapData) data).getInternal()); // Safe otherwise we would have thrown already
    }

    @SuppressWarnings("unchecked")
    private <T> ITag<T> toVanilla(final MCTag<T> tag) {
        if (tag == null) return null;
        return (ITag<T>) tag.getInternal();
    }
}
