package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.MatchToolLootConditionBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Additional methods for easier modification of item stack-related loot tables.
 */
@Document("vanilla/api/loot/modifiers/IItemStackExpansion")
@ZenCodeType.Expansion("crafttweaker.api.item.IItemStack")
@ZenRegister
public final class ModifierItemStackExpansion {
    /**
     * Adds an {@link ILootModifier} for everything that uses this item as a tool, such as block breaking.
     *
     * Additional parameters that further identify the tool, such as NBT data, damage, or count, are ignored.
     *
     * @param internal The item that should be used as a tool.
     * @param name A name for the loot modifier.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addToolModifier(final IItemStack internal, final String name, final ILootModifier modifier) {
        addToolModifier(internal, name, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} for everything that uses this item as a tool, such as block breaking, optionally
     * considering its damage.
     *
     * Additional parameters that further identify the tool, such as NBT data, or count, are ignored.
     *
     * @param internal The item that should be used as a tool.
     * @param name A name for the loot modifier.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addToolModifier(final IItemStack internal, final String name, final boolean matchDamage, final ILootModifier modifier) {
        addToolModifier(internal, name, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} for everything that uses this item as a tool, such as block breaking, optionally
     * considering its damage and count.
     *
     * Additional parameters that further identify the tool, such as NBT data, are ignored.
     *
     * @param internal The item that should be used as a tool.
     * @param name A name for the loot modifier.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param matchCount Whether to consider the amount or not when trying to match the tool.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addToolModifier(final IItemStack internal, final String name, final boolean matchDamage, final boolean matchCount, final ILootModifier modifier) {
        addToolModifier(internal, name, matchDamage, matchCount, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} for everything that uses this item as a tool, such as block breaking, optionally
     * considering its damage, count, and NBT data.
     *
     * @param internal The item that should be used as a tool.
     * @param name A name for the loot modifier.
     * @param matchDamage Whether to consider damage or not when trying to match the tool.
     * @param matchCount Whether to consider the amount or not when trying to match the tool.
     * @param matchNbt Whether to consider NBT data or not when trying to match the tool.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addToolModifier(final IItemStack internal, final String name, final boolean matchDamage, final boolean matchCount, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(MatchToolLootConditionBuilder.class, toolCondition ->
                        toolCondition.withPredicate(item -> item.matching(internal, matchDamage, matchCount, matchNbt))),
                modifier);
    }
}
