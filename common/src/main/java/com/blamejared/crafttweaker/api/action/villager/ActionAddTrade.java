package com.blamejared.crafttweaker.api.action.villager;


import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.List;

public class ActionAddTrade extends ActionTradeBase {
    
    private final VillagerTrades.ItemListing trade;
    
    public ActionAddTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing trade) {
        
        super(profession, level);
        this.trade = trade;
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
        
        return String.format("Adding Villager trade for Profession: '%s' and Level: '%s'", profession.toString(), level);
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
        
        return String.format("Undoing addition of Villager trade for Profession: '%s' and Level: '%s'", profession.toString(), level);
    }
    
}