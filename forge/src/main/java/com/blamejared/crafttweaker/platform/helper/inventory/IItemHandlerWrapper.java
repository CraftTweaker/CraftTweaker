package com.blamejared.crafttweaker.platform.helper.inventory;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class IItemHandlerWrapper implements IInventoryWrapper {
    
    private final IItemHandler handler;
    
    public IItemHandlerWrapper(IItemHandler handler) {
        
        this.handler = handler;
    }
    
    @Override
    public int getContainerSize() {
        
        return handler.getSlots();
    }
    
    @Nonnull
    @Override
    public ItemStack getItem(int slot) {
        
        return handler.getStackInSlot(slot);
    }
    
    @Override
    public boolean canFitInSlot(int slot, ItemStack stack) {
        
        ItemStack item = getItem(slot);
        if(item.isEmpty()) {
            return true;
        }
        if(ItemHandlerHelper.canItemStacksStack(item, stack)) {
            int maxStackSize = Math.min(handler.getSlotLimit(slot), item.getMaxStackSize());
            return item.getCount() + stack.getCount() < maxStackSize;
        }
        return false;
        
    }
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        
        if(slot >= 0 && slot < handler.getSlots()) {
            
            return handler.insertItem(slot, stack, simulate);
        }
        
        return stack;
    }
    
}
