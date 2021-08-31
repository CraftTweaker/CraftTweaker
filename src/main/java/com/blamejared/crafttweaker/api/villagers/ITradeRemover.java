package com.blamejared.crafttweaker.api.villagers;

import net.minecraft.entity.merchant.villager.VillagerTrades;

public interface ITradeRemover {
    
    boolean shouldRemove(VillagerTrades.ITrade trade);
    
}