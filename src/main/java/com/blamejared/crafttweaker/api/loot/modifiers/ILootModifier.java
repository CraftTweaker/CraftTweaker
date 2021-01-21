package com.blamejared.crafttweaker.api.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifiers.ILootModifier")
@Document("vanilla/api/loot/modifiers/ILootModifier")
public interface ILootModifier {
    @ZenCodeType.Method
    List<IItemStack> applyModifier(List<IItemStack> loot, LootContext currentContext);
}
