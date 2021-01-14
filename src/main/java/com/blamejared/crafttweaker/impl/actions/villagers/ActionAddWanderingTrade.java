package com.blamejared.crafttweaker.impl.actions.villagers;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraftforge.common.BasicTrade;

import java.util.List;

public class ActionAddWanderingTrade extends ActionTradeBase {
    
    private final BasicTrade trade;
    
    public ActionAddWanderingTrade(int level, BasicTrade trade) {
        super(level);
        this.trade = trade;
    }
    
    @Override
    protected Int2ObjectMap<VillagerTrades.ITrade[]> getTrades() {
        return VillagerTrades.field_221240_b;
    }
    
    @Override
    public void apply() {
        List<VillagerTrades.ITrade> tradeList = getTradeList();
        tradeList.add(trade);
        setTradeList(tradeList);
    }
    
    @Override
    public String describe() {
        return String.format("Adding Wandering Trader trade for Level: '%s'", level);
    }
    
    @Override
    public void undo() {
        List<VillagerTrades.ITrade> tradeList = getTradeList();
        tradeList.remove(trade);
        setTradeList(tradeList);
    }
    
    @Override
    public String describeUndo() {
        return String.format("Undoing addition of Wandering Trader trade for Level: '%s'", level);
    }
}