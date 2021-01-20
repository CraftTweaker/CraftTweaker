package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CraftTweakerHelper {

    public static ItemStack[] getItemStacks(IItemStack[] items) {
        return Arrays.stream(items).map(IItemStack::getInternal).toArray(ItemStack[]::new);
    }

    public static List<ItemStack> getItemStacks(List<IItemStack> items) {
        return items.stream().map(IItemStack::getInternal).collect(Collectors.toList());
    }

    public static IItemStack[] getIItemStacks(ItemStack[] items) {
        return Arrays.stream(items).map(MCItemStack::new).toArray(IItemStack[]::new);
    }

    public static List<IItemStack> getIItemStacks(List<ItemStack> items) {
        return items.stream().map(MCItemStack::new).collect(Collectors.toList());
    }

    public static Item[] getItems(IItemStack[] items) {
        return Arrays.stream(items)
                .map(iItemStack -> iItemStack.getInternal().getItem())
                .toArray(Item[]::new);
    }

    public static List<EntityType<?>> getEntityTypes(List<MCEntityType> entityTypes) {
        return entityTypes.stream().map(MCEntityType::getInternal).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T, U> ITag<U> getITag(final MCTag<T> tag) {
        return (ITag<U>) tag.getInternal();
    }
}
