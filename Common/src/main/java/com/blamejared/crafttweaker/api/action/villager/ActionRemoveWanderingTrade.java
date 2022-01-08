package com.blamejared.crafttweaker.api.action.villager;

import com.blamejared.crafttweaker.api.villager.ITradeRemover;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.ArrayList;
import java.util.List;

public class ActionRemoveWanderingTrade extends ActionTradeBase {
    
    private final ITradeRemover tradeRemover;
    private final List<VillagerTrades.ItemListing> removed;
    
    public ActionRemoveWanderingTrade(int level, ITradeRemover tradeRemover) {
        
        super(level);
        this.tradeRemover = tradeRemover;
        this.removed = new ArrayList<>();
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
        
        tradeList.forEach(iTrade -> {
            if(tradeRemover.shouldRemove(iTrade)) {
                removed.add(iTrade);
            }
        });
        tradeList.removeAll(removed);
    }
    
    @Override
    public String describe() {
        
        return String.format("Removing Wandering Trader trade for Level: '%s'", level);
        
    }
    
    @Override
    public void undo() {
        
        List<VillagerTrades.ItemListing> tradeList = getTradeList();
        undo(tradeList);
        setTradeList(tradeList);
    }
    
    @Override
    public void undo(List<VillagerTrades.ItemListing> tradeList) {
        
        tradeList.addAll(removed);
    }
    
    @Override
    public String describeUndo() {
        
        return String.format("Undoing removal of Wandering Trader trade for Level: '%s'", level);
        
    }
    
}