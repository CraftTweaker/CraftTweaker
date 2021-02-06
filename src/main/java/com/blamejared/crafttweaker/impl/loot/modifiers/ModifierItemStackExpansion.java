package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.MatchToolLootConditionBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/item/IItemStack")
@ZenCodeType.Expansion("crafttweaker.api.item.IItemStack")
@ZenRegister
public class ModifierItemStackExpansion {
    @ZenCodeType.Method
    public static void addToolModifier(final IItemStack $this, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(MatchToolLootConditionBuilder.class, toolCondition -> toolCondition.withPredicate(item -> {
                    item.withItem($this);
                    if ($this.hasTag()) {
                        item.withDataPredicate(nbt -> nbt.withData($this.getTag()));
                    }
                })),
                modifier);
    }
}
