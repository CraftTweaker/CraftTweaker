package com.blamejared.crafttweaker.api.action.villager;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.List;

public class ActionAddWanderingTrade extends ActionTradeBase {
    
    private final VillagerTrades.ItemListing trade;
    
    public ActionAddWanderingTrade(int level, VillagerTrades.ItemListing trade) {
        
        super(level);
        this.trade = trade;
    }
    
    @Override
    protected Int2ObjectMap<VillagerTrades.ItemListing[]> getTrades() {
        
        return VillagerTrades.WANDERING_TRADER_TRADES;
    }
    
    @Override
    public void apply() {
        
        List<VillagerTrades.ItemListing> tradeList = getTradeList();
        apply(tradeList);
        setTradeList(tradeList);
    }
    
    @Override
    public void apply(List<VillagerTrades.ItemListing> tradeList) {
        
        tradeList.add(trade);
    }
    
    @Override
    public String describe() {
        
        return String.format("Adding Wandering Trader trade for Level: '%s'", level);
    }
    
    @Override
    public void undo() {
        
        List<VillagerTrades.ItemListing> tradeList = getTradeList();
        undo(tradeList);
        setTradeList(tradeList);
    }
    
    @Override
    public void undo(List<VillagerTrades.ItemListing> tradeList) {
        
        tradeList.remove(trade);
    }
    
    @Override
    public String describeUndo() {
        
        return String.format("Undoing addition of Wandering Trader trade for Level: '%s'", level);
    }
    
}