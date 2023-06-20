package com.blamejared.crafttweaker.natives.event.entity.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/teleport/ChorusFruitTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.ChorusFruit.class, zenCodeName = "crafttweaker.forge.api.event.entity.teleport.ChorusFruitTeleportEvent")
public class ExpandChorusFruitTeleportEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityTeleportEvent.ChorusFruit> BUS = IEventBus.cancelable(
            EntityTeleportEvent.ChorusFruit.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    
    @ZenCodeType.Getter("livingEntity")
    public static LivingEntity getEntityLiving(EntityTeleportEvent.ChorusFruit internal) {
        
        return internal.getEntityLiving();
    }
    
}
