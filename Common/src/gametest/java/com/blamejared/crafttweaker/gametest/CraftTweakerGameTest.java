package com.blamejared.crafttweaker.gametest;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.gametest.framework.DelegatingGameTestAssertException;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface CraftTweakerGameTest {
    
    default void fail() {
        
        fail("Assertion failed!");
    }
    
    default void fail(String message) {
        
        throw new GameTestAssertException(message);
    }
    
    default void fail(Exception e) {
        
        fail(e.getMessage(), e);
    }
    
    default void fail(String message, Exception e) {
        
        throw new DelegatingGameTestAssertException(message, e);
    }
    
    
    default IItemStack immutableStack(ItemStack stack) {
        
        return IItemStack.of(stack);
    }
    
    default IItemStack immutableStack(ItemLike item) {
        
        return IItemStack.of(new ItemStack(item));
    }
    
    default IItemStack mutableStack(ItemStack stack) {
        
        return IItemStack.of(stack);
    }
    
    default IItemStack mutableStack(ItemLike item) {
        
        return IItemStack.of(new ItemStack(item));
    }
    
}
