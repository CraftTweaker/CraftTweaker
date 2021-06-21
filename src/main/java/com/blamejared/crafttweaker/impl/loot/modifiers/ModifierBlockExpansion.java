package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.ToolTypeLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.BlockStatePropertyLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.MatchToolLootConditionBuilder;
import com.blamejared.crafttweaker.impl.predicate.StatePropertiesPredicate;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * Additional methods for easier modification of block-related loot tables.
 */
@Document("vanilla/api/loot/modifiers/BlockExpansion")
@ZenCodeType.Expansion("crafttweaker.api.blocks.MCBlock")
@ZenRegister
public final class ModifierBlockExpansion {
    /**
     * Adds an {@link IItemStack} as a drop for this block.
     *
     * @param internal The block to add the item to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param stack The stack to add to the drops.
     */
    @ZenCodeType.Method
    public static void addDrop(final Block internal, final String uniqueId, final IItemStack stack) {
        addLootModifier(internal, uniqueId, CommonLootModifiers.add(stack));
    }
    
    /**
     * Adds a list of {@link IItemStack}s as drops for this block.
     *
     * @param internal The block to add the items to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param stacks The stacks to add to the drops.
     */
    @ZenCodeType.Method
    public static void addDrops(final Block internal, final String uniqueId, final IItemStack... stacks) {
        addLootModifier(internal, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    /**
     * Adds an {@link ILootModifier} to this block, with the given name.
     *
     * @param internal The block to add the loot modifier to.
     * @param name The name of the loot modifier to add.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addLootModifier(final Block internal, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(BlockStatePropertyLootConditionTypeBuilder.class, blockStateProps -> blockStateProps.withBlock(internal)),
                modifier);
    }
    
    /**
     * Adds an {@link IItemStack} to the drops of this block if it matches the state outlined in the
     * {@link StatePropertiesPredicate}.
     *
     * @param internal The block to add the loot modifier to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param statePredicate A consumer to configure the {@link StatePropertiesPredicate} to identify the target state.
     * @param stack The stack to add to the drops.
     */
    @ZenCodeType.Method
    public static void addStateDrop(final Block internal, final String uniqueId, final Consumer<StatePropertiesPredicate> statePredicate, final IItemStack stack) {
        addStateLootModifier(internal, uniqueId, statePredicate, CommonLootModifiers.add(stack));
    }
    
    /**
     * Adds an {@link ILootModifier} to this block, firing only if it matches the state outlined in the
     * {@link StatePropertiesPredicate}.
     *
     * @param internal The block to add the loot modifier to.
     * @param name The name of the loot modifier to add.
     * @param statePredicate A consumer to configure the {@link StatePropertiesPredicate} to identify the target state.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addStateLootModifier(final Block internal, final String name, final Consumer<StatePropertiesPredicate> statePredicate, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(BlockStatePropertyLootConditionTypeBuilder.class, bs -> bs.withBlock(internal).withStatePropertiesPredicate(statePredicate)),
                modifier
        );
    }
    
    /**
     * Adds an {@link IItemStack} to the drops of this block, if it gets broken with the given tool.
     *
     * Parameters that may be attached the tool such as count, damage, or NBT data are ignored.
     *
     * @param internal The block to add the loot modifier to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param tool The tool the block was broken with.
     * @param stack The stack to add to the drops.
     */
    @ZenCodeType.Method
    public static void addToolDrop(final Block internal, final String uniqueId, final IItemStack tool, final IItemStack stack) {
        addToolLootModifier(internal, uniqueId, tool, CommonLootModifiers.add(stack));
    }
    
    /**
     * Adds a list of {@link IItemStack}s to the drops of this block, if it gets broken with the given tool.
     *
     * Parameters that may be attached the tool such as count, damage, or NBT data are ignored.
     *
     * @param internal The block to add the loot modifier to.
     * @param uniqueId A unique identifier for this loot modifier.
     * @param tool The tool the block was broken with.
     * @param stacks The stacks to add to the drops.
     */
    @ZenCodeType.Method
    public static void addToolDrops(final Block internal, final String uniqueId, final IItemStack tool, final IItemStack... stacks) {
        addToolLootModifier(internal, uniqueId, tool, CommonLootModifiers.addAll(stacks));
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block gets broken with the given tool.
     *
     * Parameters that may be attached to the tool such as count, damage, or NBT data are ignored.
     *
     * @param internal The block to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param tool The tool the block was broken with.
     * @param modifier The loot modifier to add to the block.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final Block internal, final String name, final IItemStack tool, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block gets broken with the given tool, optionally considering
     * its damage.
     *
     * Additional parameters that may be attached to the tool, such as NBT or count, are ignored.
     *
     * @param internal The block to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param tool The tool the block was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param modifier The loot modifier to add to the block.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final Block internal, final String name, final IItemStack tool, final boolean matchDamage, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block gets broken with the given tool, optionally considering
     * its damage or NBT.
     *
     * Additional parameters that may be attached to the tool, such as count, are ignored.
     *
     * @param internal The block to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param tool The tool the block was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param matchNbt Whether to consider NBT data or not when trying to match the tool.
     * @param modifier The loot modifier to add to the block.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final Block internal, final String name, final IItemStack tool, final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockStatePropertyLootConditionTypeBuilder.class, bs -> bs.withBlock(internal))
                        .add(MatchToolLootConditionBuilder.class, mt -> mt.withPredicate(item -> item.matching(tool, matchDamage, false, matchNbt))),
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block gets broken with a tool with the given {@link ToolType}.
     *
     * <p>Damage or NBT is ignored when attempting to match the tool.</p>
     *
     * @param internal The block to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param toolType The type of the tool the block must be broken with.
     * @param modifier The loot modifier to add to the block.
     */
    @ZenCodeType.Method
    public static void addToolTypeLootModifier(final Block internal, final String name, final ToolType toolType, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockStatePropertyLootConditionTypeBuilder.class, bs -> bs.withBlock(internal))
                        .add(ToolTypeLootConditionTypeBuilder.class, tt -> tt.withToolType(toolType)),
                modifier
        );
    }
}
