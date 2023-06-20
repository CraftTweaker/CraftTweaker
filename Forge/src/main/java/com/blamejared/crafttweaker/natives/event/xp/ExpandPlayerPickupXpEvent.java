package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/xp/PlayerPickupXpEvent")
@NativeTypeRegistration(value = PlayerXpEvent.PickupXp.class, zenCodeName = "crafttweaker.forge.api.event.xp.PlayerPickupXpEvent")
public class ExpandPlayerPickupXpEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerXpEvent.PickupXp> BUS = IEventBus.cancelable(
            PlayerXpEvent.PickupXp.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("orb")
    public static ExperienceOrb getOrb(PlayerXpEvent.PickupXp internal) {
        
        return internal.getOrb();
    }
    
}
