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
@Document("neoforge/api/event/entity/teleport/EnderEntityTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.EnderEntity.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.teleport.EnderEntityTeleportEvent")
public class ExpandEnderEntityTeleportEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityTeleportEvent.EnderEntity> BUS = IEventBus.cancelable(
            EntityTeleportEvent.EnderEntity.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("entity")
    public static LivingEntity getEntity(EntityTeleportEvent.EnderEntity internal) {
        
        return internal.getEntityLiving();
    }
    
}
