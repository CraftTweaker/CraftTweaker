package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * Represents a predicate for a {@link Block}.
 *
 * This predicate will match a block state with either the given {@link Block} or block tag ({@link MCTag}), with the
 * second check taking precedence over the first if they are both present. If this comparison succeeds, then the
 * predicate may also verify additional block state properties via the supplied {@link StatePropertiesPredicate} or
 * specific parts of the NBT data of the block entity associated to the state via a {@link NBTPredicate}.
 *
 * By default, this predicate allows any block state to pass the checks without restrictions.
 */
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

    /**
     * Sets the block that this predicate should match.
     *
     * If a tag to match against has already been set, then the tag check will take precedence over this check.
     *
     * @param block The block the predicate should match.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public BlockPredicate withBlock(final Block block) {
        this.block = block;
        return this;
    }

    /**
     * Sets the tag that this predicate should use for matching.
     *
     * The predicate will successfully match only if the supplied block state's block is contained within the given tag.
     *
     * Specifying both a tag and a block to match against will make the tag take precedence over the block.
     *
     * @param blockTag The tag the predicate should use for matching.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public BlockPredicate withBlockTag(final MCTag<Block> blockTag) {
        this.blockTag = blockTag;
        return this;
    }

    /**
     * Creates and sets the {@link NBTPredicate} that will be matched against the block entity's data.
     *
     * Any changes that have already been made to the NBT predicate will be overwritten, effectively replacing the
     * previous one, if any.
     *
     * @param builder A consumer that will be used to configure the {@link NBTPredicate}.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public BlockPredicate withDataPredicate(final Consumer<NBTPredicate> builder) {
        final NBTPredicate predicate = new NBTPredicate();
        builder.accept(predicate);
        this.data = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link StatePropertiesPredicate} that will be matched against the block state's properties.
     *
     * Any changes that have already been made to the state properties predicate will be overwritten, effectively
     * replacing the previous one, if any.
     *
     * @param builder A consumer that will be used to configure the {@link StatePropertiesPredicate}.
     * @return This predicate for chaining.
     */
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
}
