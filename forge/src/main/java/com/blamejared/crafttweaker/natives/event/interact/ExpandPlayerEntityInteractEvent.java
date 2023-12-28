package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired on both sides whenever the player interacts with an entity.
 *
 * @docEvent canceled will cause the entity to not be interacted with
 */
@ZenRegister
@ZenEvent
@Document("forge/api/event/interact/EntityInteractEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.EntityInteract.class, zenCodeName = "crafttweaker.forge.api.event.interact.EntityInteractEvent")
public class ExpandPlayerEntityInteractEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerInteractEvent.EntityInteract> BUS = IEventBus.cancelable(
            PlayerInteractEvent.EntityInteract.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("target")
    public static Entity getTarget(PlayerInteractEvent.EntityInteract internal) {
        
        return internal.getTarget();
    }
    
}
