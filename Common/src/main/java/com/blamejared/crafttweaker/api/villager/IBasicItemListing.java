package com.blamejared.crafttweaker.api.villager;

import net.minecraft.world.item.ItemStack;

public interface IBasicItemListing {
    
    ItemStack getPrice();
    
    ItemStack getPrice2();
    
    ItemStack getForSale();
    
    int getMaxTrades();
    
    int getXp();
    
    float getPriceMult();
    
}
