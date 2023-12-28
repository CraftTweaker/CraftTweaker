package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/EntityJoinLevelEvent")
@NativeTypeRegistration(value = EntityJoinLevelEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.EntityJoinLevelEvent")
public class ExpandEntityJoinLevelEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityJoinLevelEvent> BUS = IEventBus.cancelable(
            EntityJoinLevelEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
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
