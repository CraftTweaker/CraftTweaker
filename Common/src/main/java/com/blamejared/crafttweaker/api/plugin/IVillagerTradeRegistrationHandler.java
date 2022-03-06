package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.function.Function;

public interface IVillagerTradeRegistrationHandler {
    
    <T extends VillagerTrades.ItemListing> void registerTradeConverter(Class<T> tradeClass, Function<T, CTTradeObject> tradeConverter);
    
}
