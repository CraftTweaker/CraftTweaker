package com.blamejared.crafttweaker.natives.loot.modifier;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.LootManager;
import com.blamejared.crafttweaker.api.loot.condition.LootConditions;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandLootItemBlockStatePropertyCondition;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandMatchTool;
import com.blamejared.crafttweaker.natives.predicate.ExpandItemPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandMinMaxBoundsInts;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Additional methods for easier modification of block-related loot tables.
 */
@Document("vanilla/api/loot/modifier/BlockLootModifiers")
@ZenCodeType.Expansion("crafttweaker.api.block.Block")
@ZenRegister
public final class ModifierSpecificExpandBlock {
    
    /**
     * Adds an {@link ILootModifier} to this block, with the given name.
     *
     * @param internal The block to add the loot modifier to.
     * @param name     The name of the loot modifier to add.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addLootModifier(final Block internal, final String name, final ILootModifier modifier) {
        
        LootManager.INSTANCE.getModifierManager().register(
                name,
                LootConditions.only(ExpandLootItemBlockStatePropertyCondition.create(internal)),
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} to this block, firing only if it matches the state outlined in the
     * {@link StatePropertiesPredicate}.
     *
     * @param internal       The block to add the loot modifier to.
     * @param name           The name of the loot modifier to add.
     * @param statePredicate A consumer to configure the {@link StatePropertiesPredicate} to identify the target state.
     * @param modifier       The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addStateLootModifier(final Block internal, final String name, final StatePropertiesPredicate.Builder statePredicate, final ILootModifier modifier) {
        
        LootManager.INSTANCE.getModifierManager().register(
                name,
                LootConditions.only(ExpandLootItemBlockStatePropertyCondition.create(internal)
                        .setProperties(statePredicate)),
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block gets broken with the given tool.
     *
     * <p>Parameters that may be attached to the tool such as count, damage, or NBT data are ignored.</p>
     *
     * @param internal The block to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param tool     The tool the block was broken with.
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
     * <p>Additional parameters that may be attached to the tool, such as NBT or count, are ignored.</p>
     *
     * @param internal    The block to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param tool        The tool the block was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param modifier    The loot modifier to add to the block.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final Block internal, final String name, final IItemStack tool, final boolean matchDamage, final ILootModifier modifier) {
        
        addToolLootModifier(internal, name, tool, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block gets broken with the given tool, optionally considering
     * its damage or NBT.
     *
     * <p>Additional parameters that may be attached to the tool, such as count, are ignored.</p>
     *
     * @param internal    The block to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param tool        The tool the block was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param matchNbt    Whether to consider NBT data or not when trying to match the tool.
     * @param modifier    The loot modifier to add to the block.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final Block internal, final String name, final IItemStack tool, final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        
        final ItemPredicate.Builder predicateBuilder = ExpandItemPredicate.create(tool);
        
        if(matchDamage) {
            predicateBuilder.hasDurability(ExpandMinMaxBoundsInts.exactly(tool.getDamage()));
        }
        
        if(matchNbt) {
            final CompoundTag tag = tool.getInternal().getTag();
            
            if(tag != null) {
                predicateBuilder.hasNbt(tag);
            }
        }
        
        LootManager.INSTANCE.getModifierManager().register(
                name,
                LootConditions.allOf(
                        ExpandLootItemBlockStatePropertyCondition.create(internal),
                        ExpandMatchTool.create(predicateBuilder)
                ),
                modifier
        );
    }
    
}
