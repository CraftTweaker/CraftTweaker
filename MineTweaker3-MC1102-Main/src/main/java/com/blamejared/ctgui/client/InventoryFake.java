package com.blamejared.ctgui.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

/**
 * Created by Jared.
 */
public class InventoryFake implements IInventory {

    private final int size;
    private ItemStack[] items;

    public InventoryFake(int size) {
        this.size = size;
        items = new ItemStack[size];
    }

    @Override
    public int getSizeInventory() {
        return items.length;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= this.getSizeInventory() ? null : items[index];
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(items, index, count);
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(items, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        this.items[index] = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i] = null;
        }
    }

    @Override
    public String getName() {
        return "Fake Inventory";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }
}
