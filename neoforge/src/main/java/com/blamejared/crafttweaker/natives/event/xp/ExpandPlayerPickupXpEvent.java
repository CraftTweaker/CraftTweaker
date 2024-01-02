package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.ExperienceOrb;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/xp/PlayerPickupXpEvent")
@NativeTypeRegistration(value = PlayerXpEvent.PickupXp.class, zenCodeName = "crafttweaker.neoforge.api.event.xp.PlayerPickupXpEvent")
public class ExpandPlayerPickupXpEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerXpEvent.PickupXp> BUS = IEventBus.cancelable(
            PlayerXpEvent.PickupXp.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("orb")
    public static ExperienceOrb getOrb(PlayerXpEvent.PickupXp internal) {
        
        return internal.getOrb();
    }
    
}
