package com.blamejared.crafttweaker.api.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

/**
 * Represents a modifier that gets applied to the loot dropped by a loot table.
 *
 * A modifier is something that can act on and change the loot dropped by a loot table, represented as a list of
 * {@link IItemStack}, leveraging the original {@link LootContext} that caused the loot drop.
 */
@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifiers.ILootModifier")
@Document("vanilla/api/loot/modifiers/ILootModifier")
public interface ILootModifier {
    /**
     * Applies the modifier to the drops of a loot table.
     *
     * @implSpec Implementations should not modify the contents of the <code>loot</code> list itself, and should rather
     * return a new list instead.
     *
     * @param loot The loot dropped by a loot table.
     * @param currentContext The context that caused the loot table to drop the loot.
     * @return A new list containing the modified loot, or the old list if the loot shouldn't be modified.
     *
     * @see LootContext
     */
    @ZenCodeType.Method
    List<IItemStack> applyModifier(final List<IItemStack> loot, final LootContext currentContext);
}
