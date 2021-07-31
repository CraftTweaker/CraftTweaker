package com.blamejared.crafttweaker.impl.actions.villagers;

import com.blamejared.crafttweaker.api.villagers.ITradeRemover;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;

import java.util.ArrayList;
import java.util.List;

public class ActionRemoveTrade extends ActionTradeBase {
    
    private final ITradeRemover tradeRemover;
    private final List<VillagerTrades.ITrade> removed;
    
    public ActionRemoveTrade(VillagerProfession profession, int level, ITradeRemover tradeRemover) {
        
        super(profession, level);
        this.tradeRemover = tradeRemover;
        this.removed = new ArrayList<>();
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
        
        return String.format("Removing Villager trade for Profession: '%s' and Level: '%s'", profession.toString(), level);
        
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
        
        return String.format("Undoing removal of Villager trade for Profession: '%s' and Level: '%s'", profession.toString(), level);
        
    }
    
}