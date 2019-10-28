package com.blamejared.crafttweaker.impl.script;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class DummyInventory implements IInventory {
    
    @Override
    public int getSizeInventory() {
        return 0;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    @Override
    public ItemStack getStackInSlot(int i) {
        return ItemStack.EMPTY;
    }
    
    @Override
    public ItemStack decrStackSize(int i, int i1) {
        return ItemStack.EMPTY;
    }
    
    @Override
    public ItemStack removeStackFromSlot(int i) {
        return ItemStack.EMPTY;
    }
    
    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
    
    }
    
    @Override
    public void markDirty() {
    
    }
    
    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        return false;
    }
    
    @Override
    public void clear() {
    
    }
}
