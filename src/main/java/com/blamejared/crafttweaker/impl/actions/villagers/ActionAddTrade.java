package com.blamejared.crafttweaker.impl.actions.villagers;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraftforge.common.BasicTrade;

import java.util.List;

public class ActionAddTrade extends ActionTradeBase {
    
    private final BasicTrade trade;
    
    public ActionAddTrade(VillagerProfession profession, int level, BasicTrade trade) {
        super(profession, level);
        this.trade = trade;
    }
    
    @Override
    public void apply() {
        List<VillagerTrades.ITrade> tradeList = getTradeList();
        tradeList.add(trade);
        setTradeList(tradeList);
    }
    
    @Override
    public String describe() {
        return String.format("Adding Villager trade for Profession: '%s' and Level: '%s'", profession.toString(), level);
    }
    
    @Override
    public void undo() {
        List<VillagerTrades.ITrade> tradeList = getTradeList();
        tradeList.remove(trade);
        setTradeList(tradeList);
    }
    
    @Override
    public String describeUndo() {
        return String.format("Undoing addition of Villager trade for Profession: '%s' and Level: '%s'", profession.toString(), level);
    }
}