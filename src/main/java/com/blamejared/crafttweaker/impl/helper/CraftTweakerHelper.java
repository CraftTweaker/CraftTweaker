package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CraftTweakerHelper {
    
    public static ItemStack[] getItemStacks(IItemStack[] items) {
        return Arrays.stream(items).map(IItemStack::getInternal).toArray(ItemStack[]::new);
    }
    
    public static List<IItemStack> getIItemStacks(List<ItemStack> items) {
        return items.stream().map(MCItemStack::new).collect(Collectors.toList());
    }
    
    public static Item[] getItems(IItemStack[] items) {
        return Arrays.stream(items)
                .map(iItemStack -> iItemStack.getInternal().getItem())
                .toArray(Item[]::new);
    }
}
