package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.MatchToolLootConditionBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.Item;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Additional methods for easier modification of item-related loot tables.
 */
@Document("vanilla/api/loot/modifiers/ItemExpansion")
@ZenCodeType.Expansion("crafttweaker.api.item.MCItemDefinition")
@ZenRegister
public final class ModifierItemExpansion {
    /**
     * Adds an {@link ILootModifier} for everything that uses this item as a tool, such as block breaking.
     *
     * @param internal The item that should be used as a tool.
     * @param name A name for the loot modifier.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addToolModifier(final Item internal, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(MatchToolLootConditionBuilder.class, toolCondition -> toolCondition.withPredicate(item -> item.withItem(internal))),
                modifier);
    }
}
