package com.blamejared.crafttweaker.natives.event.entity.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/teleport/ChorusFruitTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.ChorusFruit.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.teleport.ChorusFruitTeleportEvent")
public class ExpandChorusFruitTeleportEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityTeleportEvent.ChorusFruit> BUS = IEventBus.cancelable(
            EntityTeleportEvent.ChorusFruit.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    
    @ZenCodeType.Getter("livingEntity")
    public static LivingEntity getEntityLiving(EntityTeleportEvent.ChorusFruit internal) {
        
        return internal.getEntityLiving();
    }
    
}
