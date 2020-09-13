package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.blocks.MCBlock;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.fluid.*;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CraftTweakerHelper {
    
    public static ItemStack[] getItemStacks(IItemStack[] items) {
        return Arrays.stream(items).map(IItemStack::getInternal).toArray(ItemStack[]::new);
    }
    
    public static IItemStack[] getIItemStacks(ItemStack[] items) {
        return Arrays.stream(items).map(MCItemStack::new).toArray(IItemStack[]::new);
    }
    
    public static List<IItemStack> getIItemStacks(List<ItemStack> items) {
        return items.stream().map(MCItemStack::new).collect(Collectors.toList());
    }
    
    public static Item[] getItems(IItemStack[] items) {
        return Arrays.stream(items).map(iItemStack -> iItemStack.getInternal().getItem()).toArray(Item[]::new);
    }
    
    public static Block[] getBlocks(MCBlock[] blocks) {
        return Arrays.stream(blocks).map(MCBlock::getInternal).toArray(Block[]::new);
    }
    
    public static EntityType<?>[] getEntityTypes(MCEntityType[] entities) {
        return Arrays.stream(entities).map(MCEntityType::getInternal).toArray(EntityType[]::new);
    }
    
    public static Fluid[] getFluids(MCFluid[] fluids) {
        return Arrays.stream(fluids).map(MCFluid::getInternal).toArray(Fluid[]::new);
    }
}
