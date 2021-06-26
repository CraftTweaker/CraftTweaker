package com.blamejared.crafttweaker.impl.loot.modifiers;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.BlockTagLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.ToolTypeLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.MatchToolLootConditionBuilder;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Additional methods for easier modification of loot tables of blocks contained in tags.
 */
@Document("vanilla/api/loot/modifiers/BlockTagExpansion")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.blocks.MCBlock>")
@ZenRegister
public class ModifierBlockTagExpansion {
    
    /**
     * Adds an {@link ILootModifier} to all the blocks contained in this tag, with the given name.
     *
     * @param internal The block tag to add the loot modifier to.
     * @param name The name of the loot modifier to add.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addLootModifier(final MCTag<Block> internal, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockTagLootConditionTypeBuilder.class, blockTag -> blockTag.withTag(internal)),
                modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if any of the blocks contained in this tag gets broken with the given
     * tool.
     *
     * <p>Parameters that may be attached to the tool such as count, damage, or NBT data are ignored.</p>
     *
     * @param internal The block tag to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param tool The tool one of the blocks contained in the tag was broken with.
     * @param modifier The loot modifier to add to the block tag.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final MCTag<Block> internal, final String name, final IItemStack tool, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if any of the blocks contained in this tag gets broken with the given
     * tool, optionally considering its damage.
     *
     * <p>Additional parameters that may be attached to the tool, such as NBT or count, are ignored.</p>
     *
     * @param internal The block tag to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param tool The tool one of the blocks contained in the tag was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param modifier The loot modifier to add to the block tag.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final MCTag<Block> internal, final String name, final IItemStack tool, final boolean matchDamage, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if any of the blocks contained in this tag gets broken with the given
     * tool, optionally considering its damage or NBT.
     *
     * <p>Additional parameters that may be attached to the tool, such as count, are ignored.</p>
     *
     * @param internal The block tag to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param tool The tool one of the blocks contained in the tag was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param matchNbt Whether to consider NBT data or not when trying to match the tool.
     * @param modifier The loot modifier to add to the block tag.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final MCTag<Block> internal, final String name, final IItemStack tool,
                                           final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockTagLootConditionTypeBuilder.class, bs -> bs.withTag(internal))
                        .add(MatchToolLootConditionBuilder.class, mt -> mt.withPredicate(item -> item.matching(tool, matchDamage, false, matchNbt))),
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if any of the blocks contained in this tag gets broken with a tool with
     * the given {@link ToolType}.
     *
     * <p>Damage or NBT is ignored when attempting to match the tool.</p>
     *
     * @param internal The block tag to add the loot modifier to.
     * @param name The name of the loot modifier.
     * @param toolType The type of the tool one of the blocks contained in this tag must be broken with.
     * @param modifier The loot modifier to add to the block tag.
     */
    @ZenCodeType.Method
    public static void addToolTypeLootModifier(final MCTag<Block> internal, final String name, final ToolType toolType, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockTagLootConditionTypeBuilder.class, bs -> bs.withTag(internal))
                        .add(ToolTypeLootConditionTypeBuilder.class, tt -> tt.withToolType(toolType)),
                modifier
        );
    }
}
