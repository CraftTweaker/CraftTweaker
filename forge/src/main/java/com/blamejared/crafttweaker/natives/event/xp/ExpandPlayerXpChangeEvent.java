package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/xp/PlayerXpChangeEvent")
@NativeTypeRegistration(value = PlayerXpEvent.XpChange.class, zenCodeName = "crafttweaker.forge.api.event.xp.PlayerXpChangeEvent")
public class ExpandPlayerXpChangeEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerXpEvent.XpChange> BUS = IEventBus.cancelable(
            PlayerXpEvent.XpChange.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("amount")
    public static int getAmount(PlayerXpEvent.XpChange internal) {
        
        return internal.getAmount();
    }
    
    @ZenCodeType.Setter("amount")
    public static void setAmount(PlayerXpEvent.XpChange internal, int amount) {
        
        internal.setAmount(amount);
    }
    
}
