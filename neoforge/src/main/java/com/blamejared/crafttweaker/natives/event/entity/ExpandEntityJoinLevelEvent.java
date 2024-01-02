package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/EntityJoinLevelEvent")
@NativeTypeRegistration(value = EntityJoinLevelEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.EntityJoinLevelEvent")
public class ExpandEntityJoinLevelEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityJoinLevelEvent> BUS = IEventBus.cancelable(
            EntityJoinLevelEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(EntityJoinLevelEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("loadedFromDisk")
    public static boolean loadedFromDisk(EntityJoinLevelEvent internal) {
        
        return internal.loadedFromDisk();
    }
    
}
