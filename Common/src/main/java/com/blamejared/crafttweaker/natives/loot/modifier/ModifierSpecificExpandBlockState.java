package com.blamejared.crafttweaker.natives.loot.modifier;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.LootManager;
import com.blamejared.crafttweaker.api.loot.condition.LootConditions;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandInvertedLootItemCondition;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandLootItemBlockStatePropertyCondition;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandMatchTool;
import com.blamejared.crafttweaker.natives.predicate.ExpandEnchantmentPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandItemPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandMinMaxBoundsInts;
import com.blamejared.crafttweaker.natives.predicate.ExpandStatePropertiesPredicate;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Additional methods for easier modification of block state-related loot tables.
 */
@Document("vanilla/api/loot/modifier/BlockStateLootModifiers")
@ZenCodeType.Expansion("crafttweaker.api.block.BlockState")
@ZenRegister
public final class ModifierSpecificExpandBlockState {
    
    /**
     * Adds an {@link ILootModifier} to this block, ignoring the current state.
     *
     * @param internal The block state to add the drops to.
     * @param name     The name of the loot modifier.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addBlockLootModifier(final BlockState internal, final String name, final ILootModifier modifier) {
        
        ModifierSpecificExpandBlock.addLootModifier(internal.getBlock(), name, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} to the current block, only if it matches the current block state precisely.
     *
     * @param internal The block state to add the drops to.
     * @param name     The name of the loot modifier.
     * @param modifier The loot modifier to add to the block state.
     */
    @ZenCodeType.Method
    public static void addTargetedLootModifier(final BlockState internal, final String name, final ILootModifier modifier) {
        
        final StatePropertiesPredicate.Builder properties = ExpandStatePropertiesPredicate.create();
        internal.getProperties().forEach(it -> properties.hasProperty(it, internal.getValue(it).toString()));
        
        LootManager.INSTANCE.getModifierManager().register(
                name,
                LootConditions.only(
                        ExpandLootItemBlockStatePropertyCondition.create(internal.getBlock()).setProperties(properties)
                ),
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} to this block, with the given name, only if it is not harvested with the silk touch enchantment.
     *
     * @param internal The block to add the loot modifier to.
     * @param name     The name of the loot modifier to add.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addNoSilkTouchLootModifier(final BlockState internal, final String name, final ILootModifier modifier) {
        
        final StatePropertiesPredicate.Builder properties = ExpandStatePropertiesPredicate.create();
        internal.getProperties().forEach(it -> properties.hasProperty(it, internal.getValue(it).toString()));
        
        LootManager.INSTANCE.getModifierManager().register(
                name,
                LootConditions.allOf(
                        ExpandLootItemBlockStatePropertyCondition.create(internal.getBlock()).setProperties(properties),
                        ExpandInvertedLootItemCondition.create(ExpandMatchTool.create(ExpandItemPredicate.create()
                                .hasEnchantment(ExpandEnchantmentPredicate.create(Enchantments.SILK_TOUCH))))
                ),
                
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block state gets broken with the given tool.
     *
     * <p>Parameters that may be attached to the tool such as count, damage, or NBT data are ignored.</p>
     *
     * @param internal The block state to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param tool     The tool the block state was broken with.
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
     * <p>Additional parameters that may be attached to the tool, such as NBT or count, are ignored.</p>
     *
     * @param internal    The block state to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param tool        The tool the block state was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param modifier    The loot modifier to add to the block state.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState internal, final String name, final IItemStack tool, final boolean matchDamage, final ILootModifier modifier) {
        
        addToolLootModifier(internal, name, tool, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} that fires if this block state gets broken with the given tool, optionally
     * considering its damage or NBT.
     *
     * <p>Additional parameters that may be attached to the tool, such as count, are ignored.</p>
     *
     * @param internal    The block state to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param tool        The tool the block state was broken with.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param matchNbt    Whether to consider NBT data or not when trying to match the tool.
     * @param modifier    The loot modifier to add to the block state.
     */
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState internal, final String name, final IItemStack tool, final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        
        final StatePropertiesPredicate.Builder properties = ExpandStatePropertiesPredicate.create();
        internal.getProperties().forEach(it -> properties.hasProperty(it, internal.getValue(it).toString()));
        
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
                        ExpandLootItemBlockStatePropertyCondition.create(internal.getBlock()).setProperties(properties),
                        ExpandMatchTool.create(predicateBuilder)
                ),
                modifier
        );
    }
    
}
