package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl_native.blocks.*;
import com.blamejared.crafttweaker.impl.entity.*;
import com.blamejared.crafttweaker.impl.fluid.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl_native.entity.MCEntityType;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;

import java.util.*;
import java.util.stream.*;

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
    
    public static List<Item> getItemsFromDefinitions(List<MCItemDefinition> definitions) {
        return definitions.stream().map(MCItemDefinition::getInternal).collect(Collectors.toList());
    }
    
    public static List<Block> getBlocks(List<ExpandBlock> toAdd) {
        return toAdd.stream().map(ExpandBlock::getInternal).collect(Collectors.toList());
    }
    
    public static List<EntityType<?>> getEntityTypes(List<MCEntityType> toAdd) {
        return toAdd.stream().map(MCEntityType::getInternal).collect(Collectors.toList());
    }
    
    public static List<Fluid> getFluids(List<MCFluid> toRemove) {
        return toRemove.stream().map(MCFluid::getInternal).collect(Collectors.toList());
    }
}
