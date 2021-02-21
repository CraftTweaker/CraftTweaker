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
public final class ModifierItemStackExpansion {
    @ZenCodeType.Method
    public static void addToolModifier(final IItemStack internal, final String name, final ILootModifier modifier) {
        addToolModifier(internal, name, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolModifier(final IItemStack internal, final String name, final boolean matchDamage, final ILootModifier modifier) {
        addToolModifier(internal, name, matchDamage, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolModifier(final IItemStack internal, final String name, final boolean matchDamage, final boolean matchCount, final ILootModifier modifier) {
        addToolModifier(internal, name, matchDamage, matchCount, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolModifier(final IItemStack internal, final String name, final boolean matchDamage, final boolean matchCount, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(MatchToolLootConditionBuilder.class, toolCondition ->
                        toolCondition.withPredicate(item -> item.matching(internal, matchDamage, matchCount, matchNbt))),
                modifier);
    }
}
