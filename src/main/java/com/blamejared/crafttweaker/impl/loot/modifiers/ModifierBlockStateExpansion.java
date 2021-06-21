package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.BlockStateLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.ToolTypeLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.MatchToolLootConditionBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Additional methods for easier modification of block state-related loot tables.
 */
@Document("vanilla/api/loot/modifiers/BlockStateExpansion")
@ZenCodeType.Expansion("crafttweaker.api.blocks.MCBlockState")
@ZenRegister
public final class ModifierBlockStateExpansion {
    /**
     * Adds an {@link IItemStack} to the drop for this block, ignoring the current state.
     *
     * @param internal The block state to add the drop to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param stack The stack to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use {@link #addBlockLootModifier(BlockState, String, ILootModifier)}
     * instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addBlockDrop(final BlockState internal, final String uniqueId, final IItemStack stack) {
        ModifierBlockExpansion.addDrop(internal.getBlock(), uniqueId, stack);
    }
    
    /**
     * Adds a list of {@link IItemStack}s to the drops for this block, ignoring the current state.
     *
     * @param internal The block state to add the drops to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param stack The stack to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use {@link #addBlockLootModifier(BlockState, String, ILootModifier)}
     * instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addBlockDrops(final BlockState internal, final String uniqueId, final IItemStack stack) {
        ModifierBlockExpansion.addDrops(internal.getBlock(), uniqueId, stack);
    }
    
    /**
     * Adds an {@link ILootModifier} to this block, ignoring the current state.
     *
     * @param internal The block state to add the drops to.
     * @param name The name of the loot modifier.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addBlockLootModifier(final BlockState internal, final String name, final ILootModifier modifier) {
        ModifierBlockExpansion.addLootModifier(internal.getBlock(), name, modifier);
    }
    
    /**
     * Adds an {@link IItemStack} to the drops of the current block, only if it matches the current block state
     * precisely.
     *
     * @param internal The block state to add the drops to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param stack The stack to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addTargetedLootModifier(BlockState, String, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addTargetedDrop(final BlockState internal, final String uniqueId, final IItemStack stack) {
        addTargetedLootModifier(internal, uniqueId, CommonLootModifiers.add(stack));
    }
    
    /**
     * Adds a list of {@link IItemStack}s to the drops of the current block, only if it matches the current block state
     * precisely.
     *
     * @param internal The block state to add the drops to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param stacks The stacks to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addTargetedLootModifier(BlockState, String, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addTargetedDrops(final BlockState internal, final String uniqueId, final IItemStack... stacks) {
        addTargetedLootModifier(internal, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    /**
     * Adds an {@link ILootModifier} to the current block, only if it matches the current block state precisely.
     *
     * @param internal The block state to add the drops to.
     * @param name The name of the loot modifier.
     * @param modifier The loot modifier to add to the block state.
     */
    @ZenCodeType.Method
    public static void addTargetedLootModifier(final BlockState internal, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(BlockStateLootConditionTypeBuilder.class, blockState -> blockState.withState(internal)),
                modifier);
    }
    
    /**
     * Adds an {@link IItemStack} to the drops of this block, if it gets broken with the given tool and matches the
     * current block state precisely.
     *
     * <p>Parameters that may be attached the tool such as count, damage, or NBT data are ignored.</p>
     *
     * @param internal The block state to add the loot modifier to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param tool The tool the block state was broken with.
     * @param stack The stack to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addToolLootModifier(BlockState, String, IItemStack, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addToolDrop(final BlockState internal, final String uniqueId, final IItemStack tool, final IItemStack stack) {
        addToolLootModifier(internal, uniqueId, tool, CommonLootModifiers.add(stack));
    }
    
    /**
     * Adds a list of {@link IItemStack}s to the drops of this block, if it gets broken with the given tool and matches
     * the current block state precisely.
     *
     * Parameters that may be attached the tool such as count, damage, or NBT data are ignored.
     *
     * @param internal The block state to add the loot modifier to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param tool The tool the block state was broken with.
     * @param stacks The stacks to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addToolLootModifier(BlockState, String, IItemStack, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addToolDrops(final BlockState internal, final String uniqueId, final IItemStack tool, final IItemStack... stacks) {
        addToolLootModifier(internal, uniqueId, tool, CommonLootModifiers.addAll(stacks));
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block state gets broken with the given tool.
     *
     * Parameters that may be attached to the tool such as count, damage, or NBT data are ignored.
     *
     * @param internal The block state to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param tool The tool the block state was broken with.
     * @param modifier The loot modifier to add to the block state.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState internal, final String name, final IItemStack tool, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block state gets broken with the given tool, optionally
     * considering its damage.
     *
     * Additional parameters that may be attached to the tool, such as NBT or count, are ignored.
     *
     * @param internal The block state to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param tool The tool the block state was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param modifier The loot modifier to add to the block state.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState internal, final String name, final IItemStack tool, final boolean matchDamage, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block state gets broken with the given tool, optionally
     * considering its damage or NBT.
     *
     * Additional parameters that may be attached to the tool, such as count, are ignored.
     *
     * @param internal The block state to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param tool The tool the block state was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param matchNbt Whether to consider NBT data or not when trying to match the tool.
     * @param modifier The loot modifier to add to the block state.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState internal, final String name, final IItemStack tool, final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockStateLootConditionTypeBuilder.class, bs -> bs.withState(internal))
                        .add(MatchToolLootConditionBuilder.class, mt -> mt.withPredicate(item -> item.matching(tool, matchDamage, false, matchNbt))),
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block state gets broken with a tool with the given
     * {@link ToolType}.
     *
     * <p>Damage or NBT is ignored when attempting to match the tool.</p>
     *
     * @param internal The block state to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param toolType The type of the tool the block state must be broken with.
     * @param modifier The loot modifier to add to the block state.
     */
    @ZenCodeType.Method
    public static void addToolTypeLootModifier(final BlockState internal, final String name, final ToolType toolType, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockStateLootConditionTypeBuilder.class, bs -> bs.withState(internal))
                        .add(ToolTypeLootConditionTypeBuilder.class, tt -> tt.withToolType(toolType)),
                modifier
        );
    }
    
}
