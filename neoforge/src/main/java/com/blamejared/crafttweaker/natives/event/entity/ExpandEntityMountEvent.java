package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/EntityMountEvent")
@NativeTypeRegistration(value = EntityMountEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.EntityMountEvent")
public class ExpandEntityMountEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityMountEvent> BUS = IEventBus.cancelable(
            EntityMountEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("mounting")
    public static boolean isMounting(EntityMountEvent internal) {
        
        return internal.isMounting();
    }
    
    @ZenCodeType.Getter("dismounting")
    public static boolean isDismounting(EntityMountEvent internal) {
        
        return internal.isDismounting();
    }
    
    @ZenCodeType.Getter("entityMounting")
    public static Entity getEntityMounting(EntityMountEvent internal) {
        
        return internal.getEntityMounting();
    }
    
    @ZenCodeType.Getter("entityBeingMounted")
    public static Entity getEntityBeingMounted(EntityMountEvent internal) {
        
        return internal.getEntityBeingMounted();
    }
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(EntityMountEvent internal) {
        
        return internal.getLevel();
    }
    
}
