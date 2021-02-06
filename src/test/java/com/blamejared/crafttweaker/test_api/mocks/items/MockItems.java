package com.blamejared.crafttweaker.test_api.mocks.items;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MockItems {
    
    public IItemStack empty = MCItemStack.EMPTY.get();
    public IItemStack bedrock = new MCItemStack(new ItemStack(Items.BEDROCK));
    public IItemStack redstone = new MCItemStack(new ItemStack(Items.REDSTONE));
    public IItemStack ironIngot = new MCItemStack(new ItemStack(Items.IRON_INGOT));
    public IItemStack ironSword = new MCItemStack(new ItemStack(Items.IRON_SWORD));
    public IItemStack ironNugget = new MCItemStack(new ItemStack(Items.IRON_NUGGET));
    
    
}
