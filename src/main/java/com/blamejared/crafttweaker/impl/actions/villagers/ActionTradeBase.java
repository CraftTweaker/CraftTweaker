package com.blamejared.crafttweaker.impl.actions.villagers;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ActionTradeBase implements IUndoableAction {
    
    protected VillagerProfession profession;
    protected final int level;
    
    public ActionTradeBase(int level) {
        this.level = level;
    }
    
    public ActionTradeBase(VillagerProfession profession, int level) {
        this.profession = profession;
        this.level = level;
    }
    
    public abstract void apply(List<VillagerTrades.ITrade> tradeList);
    
    public abstract void undo(List<VillagerTrades.ITrade> tradeList);
    
    protected Int2ObjectMap<VillagerTrades.ITrade[]> getTrades() {
        return VillagerTrades.VILLAGER_DEFAULT_TRADES.computeIfAbsent(profession, villagerProfession -> new Int2ObjectArrayMap<>());
    }
    
    protected List<VillagerTrades.ITrade> getTradeList() {
        VillagerTrades.ITrade[] iTrades = getTrades().computeIfAbsent(level, integer -> new VillagerTrades.ITrade[0]);
        return new ArrayList<>(Arrays.asList(iTrades));
    }
    
    protected void setTradeList(List<VillagerTrades.ITrade> tradeList) {
        getTrades().put(level, tradeList.toArray(new VillagerTrades.ITrade[0]));
    }
    
    public int getLevel() {
        
        return level;
    }
    
    public VillagerProfession getProfession() {
        
        return profession;
    }
    
}
