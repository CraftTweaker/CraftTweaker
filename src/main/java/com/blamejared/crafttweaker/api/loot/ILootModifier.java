package com.blamejared.crafttweaker.api.loot;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.loot.MCLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.ILootModifier")
@Document("vanilla/api/loot/ILootModifier")
public interface ILootModifier {
    @ZenCodeType.Method
    List<IItemStack> applyModifier(List<IItemStack> loot, MCLootContext currentContext);
}
