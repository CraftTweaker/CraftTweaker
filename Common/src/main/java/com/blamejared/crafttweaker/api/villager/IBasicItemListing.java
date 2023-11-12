package com.blamejared.crafttweaker.api.villager;

import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.ItemStack;

public interface IBasicItemListing {
    
    default ItemStack getPrice() {
        
        return Services.PLATFORM.getBasicTradePrice(this);
    }
    
    
    default ItemStack getPrice2() {
        
        return Services.PLATFORM.getBasicTradePrice2(this);
    }
    
    default ItemStack getForSale() {
        
        return Services.PLATFORM.getBasicTradeForSale(this);
    }
    
    default int getMaxTrades() {
        
        return Services.PLATFORM.getBasicTradeMaxTrades(this);
    }
    
    default int getXp() {
        
        return Services.PLATFORM.getBasicTradeXp(this);
    }
    
    default float getPriceMult() {
        
        return Services.PLATFORM.getBasicTradePriceMult(this);
    }
    
}
