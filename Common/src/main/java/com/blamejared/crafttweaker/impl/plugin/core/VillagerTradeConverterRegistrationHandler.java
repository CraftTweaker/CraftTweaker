package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IVillagerTradeRegistrationHandler;
import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import com.blamejared.crafttweaker.api.villager.CTVillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.function.Consumer;
import java.util.function.Function;

public final class VillagerTradeConverterRegistrationHandler implements IVillagerTradeRegistrationHandler {
    
    @Override
    public <T extends VillagerTrades.ItemListing> void registerTradeConverter(Class<T> tradeClass, Function<T, CTTradeObject> tradeConverter) {
    
        CTVillagerTrades.TRADE_CONVERTER.put((Class<VillagerTrades.ItemListing>) tradeClass, (Function<VillagerTrades.ItemListing, CTTradeObject>) tradeConverter);
    }
    
    static void gather(final Consumer<IVillagerTradeRegistrationHandler> consumer) {
        
        final VillagerTradeConverterRegistrationHandler handler = new VillagerTradeConverterRegistrationHandler();
        consumer.accept(handler);
    }
    
}
