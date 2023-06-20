package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.player.TradeWithVillagerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/TradeWithVillagerEvent")
@NativeTypeRegistration(value = TradeWithVillagerEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.TradeWithVillagerEvent")
public class ExpandTradeWithVillagerEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<TradeWithVillagerEvent> BUS = IEventBus.direct(
            TradeWithVillagerEvent.class,
            ForgeEventBusWire.of()
    );
    
    
    @ZenCodeType.Getter("merchantOffer")
    public static MerchantOffer getMerchantOffer(TradeWithVillagerEvent internal) {
        
        return internal.getMerchantOffer();
    }
    
    @ZenCodeType.Getter("abstractVillager")
    public static AbstractVillager getAbstractVillager(TradeWithVillagerEvent internal) {
        
        return internal.getAbstractVillager();
    }
    
}
