package com.blamejared.crafttweaker.api.action.villager;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ActionTradeBase extends CraftTweakerAction implements IUndoableAction {
    
    protected VillagerProfession profession;
    protected final int level;
    
    public ActionTradeBase(int level) {
        
        this.level = level;
    }
    
    public ActionTradeBase(VillagerProfession profession, int level) {
        
        this.profession = profession;
        this.level = level;
    }
    
    public abstract void apply(List<VillagerTrades.ItemListing> tradeList);
    
    public abstract void undo(List<VillagerTrades.ItemListing> tradeList);
    
    protected Int2ObjectMap<VillagerTrades.ItemListing[]> getTrades() {
        
        return VillagerTrades.TRADES.computeIfAbsent(profession, villagerProfession -> new Int2ObjectArrayMap<>());
    }
    
    protected List<VillagerTrades.ItemListing> getTradeList() {
        
        VillagerTrades.ItemListing[] iTrades = getTrades().computeIfAbsent(level, integer -> new VillagerTrades.ItemListing[0]);
        return new ArrayList<>(Arrays.asList(iTrades));
    }
    
    protected void setTradeList(List<VillagerTrades.ItemListing> tradeList) {
        
        getTrades().put(level, tradeList.toArray(new VillagerTrades.ItemListing[0]));
    }
    
    public int getLevel() {
        
        return level;
    }
    
    public VillagerProfession getProfession() {
        
        return profession;
    }
    
}
