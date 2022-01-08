package com.blamejared.crafttweaker.api.loot.modifier;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.natives.item.ExpandItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a modifier that gets applied to the loot dropped by a loot table.
 *
 * <p>A modifier is something that can act on and change the loot dropped by a loot table, represented as a list of
 * {@link IItemStack}, leveraging the original {@link LootContext} that caused the loot drop.</p>
 */
@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifier.ILootModifier")
@Document("vanilla/api/loot/modifier/ILootModifier")
public interface ILootModifier {
    
    ILootModifier DEFAULT = (loot, context) -> loot;
    
    /**
     * Applies the modifier to the drops of a loot table.
     *
     * @param loot    The loot dropped by a loot table.
     * @param context The context that caused the loot table to drop the loot.
     *
     * @return A list containing the modified loot.
     *
     * @see LootContext
     */
    @ZenCodeType.Method
    List<IItemStack> modify(final List<IItemStack> loot, final LootContext context);
    
    /**
     * Applies the modifier to the drops of a loot table.
     *
     * @param loot    The loot dropped by a loot table.
     * @param context The context that caused the loot table to drop the loot.
     *
     * @return A list containing the modified loot.
     *
     * @see LootContext
     */
    default List<ItemStack> doApply(final List<ItemStack> loot, final LootContext context) {
        
        return this.modify(loot.stream().map(ExpandItemStack::asIItemStack).collect(Collectors.toList()), context)
                .stream()
                .map(IItemStack::getImmutableInternal)
                .collect(Collectors.toList());
    }
    
}