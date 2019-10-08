package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.blocks.MCBlock;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class CraftTweakerHelper {
    
    public static ItemStack[] getItemStacks(IItemStack[] items) {
        return Arrays.stream(items).map(IItemStack::getInternal).toArray(ItemStack[]::new);
    }
    
    public static Item[] getItems(IItemStack[] items) {
        return Arrays.stream(items).map(iItemStack -> iItemStack.getInternal().getItem()).toArray(Item[]::new);
    }
    
    public static Block[] getBlocks(MCBlock[] blocks) {
        return Arrays.stream(blocks).map(MCBlock::getInternal).toArray(Block[]::new);
    }
    
    public static EntityType[] getEntityTypes(MCEntityType[] entities) {
        return Arrays.stream(entities).map(MCEntityType::getInternal).toArray(EntityType[]::new);
    }
    
}
