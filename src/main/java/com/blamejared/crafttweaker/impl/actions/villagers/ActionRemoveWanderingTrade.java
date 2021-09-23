package com.blamejared.crafttweaker.impl.actions.villagers;

import com.blamejared.crafttweaker.api.villagers.ITradeRemover;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerTrades;

import java.util.ArrayList;
import java.util.List;

public class ActionRemoveWanderingTrade extends ActionTradeBase {
    
    private final ITradeRemover tradeRemover;
    private final List<VillagerTrades.ITrade> removed;
    
    public ActionRemoveWanderingTrade(int level, ITradeRemover tradeRemover) {
        
        super(level);
        this.tradeRemover = tradeRemover;
        this.removed = new ArrayList<>();
    }
    
    @Override
    protected Int2ObjectMap<VillagerTrades.ITrade[]> getTrades() {
        
        return VillagerTrades.field_221240_b;
    }
    
    @Override
    public void apply() {
        
        List<VillagerTrades.ITrade> tradeList = getTradeList();
        apply(tradeList);
        setTradeList(tradeList);
    }
    
    @Override
    public void apply(List<VillagerTrades.ITrade> tradeList) {
        
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
        
        List<VillagerTrades.ITrade> tradeList = getTradeList();
        undo(tradeList);
        setTradeList(tradeList);
    }
    
    @Override
    public void undo(List<VillagerTrades.ITrade> tradeList) {
        
        tradeList.addAll(removed);
    }
    
    @Override
    public String describeUndo() {
        
        return String.format("Undoing removal of Wandering Trader trade for Level: '%s'", level);
        
    }
    
}