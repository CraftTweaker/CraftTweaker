package com.blamejared.crafttweaker.api.villager;


import net.minecraft.world.entity.npc.VillagerTrades;

public interface ITradeRemover {
    
    boolean shouldRemove(VillagerTrades.ItemListing trade);
    
}