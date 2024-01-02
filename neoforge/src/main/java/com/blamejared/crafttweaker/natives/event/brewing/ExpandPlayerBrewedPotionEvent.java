package com.blamejared.crafttweaker.natives.event.brewing;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.brewing.PlayerBrewedPotionEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/brewing/PlayerBrewedPotionEvent")
@NativeTypeRegistration(value = PlayerBrewedPotionEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.brewing.PlayerBrewedPotionEvent")
public class ExpandPlayerBrewedPotionEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerBrewedPotionEvent> BUS = IEventBus.direct(
            PlayerBrewedPotionEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("stack")
    public static IItemStack getStack(PlayerBrewedPotionEvent internal) {
        
        return IItemStack.of(internal.getStack());
    }
    
}
