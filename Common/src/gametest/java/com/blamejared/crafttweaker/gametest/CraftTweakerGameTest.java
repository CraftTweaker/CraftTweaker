package com.blamejared.crafttweaker.gametest;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.gametest.truth.IExpectIData;
import com.blamejared.crafttweaker.gametest.truth.IExpectIIngredient;
import com.blamejared.crafttweaker.gametest.truth.IExpectVanilla;
import com.blamejared.crafttweaker.gametest.truth.ITruth8Wrapper;
import com.blamejared.crafttweaker.gametest.truth.ITruthWrapper;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface CraftTweakerGameTest extends ITruthWrapper, ITruth8Wrapper, IExpectIData, IExpectIIngredient, IExpectVanilla {
    
    
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
        
        e.printStackTrace();
        GameTestAssertException gtae = new GameTestAssertException(message);
        gtae.setStackTrace(e.getStackTrace());
        throw gtae;
    }
    
    
    default IItemStack immutableStack(ItemStack stack) {
        
        return Services.PLATFORM.createMCItemStack(stack);
    }
    
    default IItemStack immutableStack(ItemLike item) {
        
        return Services.PLATFORM.createMCItemStack(new ItemStack(item));
    }
    
    default IItemStack mutableStack(ItemStack stack) {
        
        return Services.PLATFORM.createMCItemStackMutable(stack);
    }
    
    default IItemStack mutableStack(ItemLike item) {
        
        return Services.PLATFORM.createMCItemStackMutable(new ItemStack(item));
    }
    
}
