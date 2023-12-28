package com.blamejared.crafttweaker.platform.helper.inventory;

import net.minecraft.world.item.ItemStack;

import java.util.OptionalInt;

public interface IInventoryWrapper {
    
    int getContainerSize();
    
    ItemStack getItem(int slot);
    
    boolean canFitInSlot(int slot, ItemStack stack);
    
    default OptionalInt getSlotFor(ItemStack stack) {
        
        if(stack.isEmpty()) {
            return OptionalInt.empty();
        }
        
        int firstEmptySlot = -1;
        
        // Loop over all the slots
        for(int i = 0; i < getContainerSize(); i++) {
            // Check if the item is empty
            if(getItem(i).isEmpty()) {
                // Have we found the first slot yet?
                if(firstEmptySlot == -1) {
                    // Set the first slot
                    firstEmptySlot = i;
                }
            } else {
                // We have a stack, time to see if it is a good stack
                if(canFitInSlot(i, stack)) {
                    // It is, lets return it
                    return OptionalInt.of(i);
                }
            }
        }
        
        return OptionalInt.empty();
    }
    
    ItemStack insertItem(int slot, ItemStack stack, boolean simulate);
    
}
