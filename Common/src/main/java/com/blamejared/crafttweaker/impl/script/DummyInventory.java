package com.blamejared.crafttweaker.impl.script;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DummyInventory implements Container {
    
    @Override
    public int getContainerSize() {
        
        return 0;
    }
    
    @Override
    public boolean isEmpty() {
        
        return false;
    }
    
    @Override
    public ItemStack getItem(int i) {
        
        return null;
    }
    
    @Override
    public ItemStack removeItem(int i, int i1) {
        
        return null;
    }
    
    @Override
    public ItemStack removeItemNoUpdate(int i) {
        
        return null;
    }
    
    @Override
    public void setItem(int i, ItemStack itemStack) {
    
    }
    
    @Override
    public void setChanged() {
    
    }
    
    @Override
    public boolean stillValid(Player player) {
        
        return false;
    }
    
    @Override
    public void clearContent() {
    
    }
    
}
