package com.blamejared.crafttweaker.platform.helper.world.inventory;

import com.blamejared.crafttweaker.platform.helper.inventory.IInventoryWrapper;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.impl.transfer.item.ItemVariantImpl;
import net.minecraft.world.item.ItemStack;

/**
 * Transfer Api inventory wrapper
 */
@SuppressWarnings("UnstableApiUsage")
public class TAInventoryWrapper implements IInventoryWrapper {
    
    private final InventoryStorage storage;
    
    public TAInventoryWrapper(InventoryStorage storage) {
    
        this.storage = storage;
    }
    
    @Override
    public int getContainerSize() {
        
        return storage.getSlots().size();
    }
    
    @Override
    public boolean canFitInSlot(int slot, ItemStack stack) {
        
        SingleSlotStorage<ItemVariant> storageSlot = storage.getSlot(slot);
        if(storageSlot.isResourceBlank()) {
            return true;
        }
        
        if(storageSlot.getResource().matches(stack)) {
            int maxStackSize = (int) Math.min(storageSlot.getCapacity(), stack.getMaxStackSize());
            return storageSlot.getAmount() + stack.getCount() < maxStackSize;
        }
        return false;
    }
    
    @Override
    public ItemStack getItem(int slot) {
        
        SingleSlotStorage<ItemVariant> slotStorage = storage.getSlot(slot);
        return slotStorage.getResource().toStack((int) slotStorage.getAmount());
    }
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        
        ItemStack copied = stack.copy();
        long needToInsert = stack.getCount();
        ItemVariantImpl variant = new ItemVariantImpl(stack.getItem(), stack.getTag());
        if(slot >= 0 && slot < storage.getSlots().size()) {
            SingleSlotStorage<ItemVariant> storageSlot = storage.getSlot(slot);
            if(!storage.supportsInsertion() || !storageSlot.supportsInsertion()) {
                return stack;
            }
            if(storageSlot.getResource().matches(stack)) {
                try(Transaction transaction = Transaction.openOuter()) {
                    long insert = storageSlot.insert(variant, needToInsert, transaction);
                    transaction.commit();
                    needToInsert -= insert;
                }
            }
        }
        if(needToInsert <= 0) {
            return ItemStack.EMPTY;
        }
        copied.setCount((int) needToInsert);
        return copied;
    }
    
}
