package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/EntityLeaveLevelEvent")
@NativeTypeRegistration(value = EntityLeaveLevelEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.EntityLeaveLevelEvent")
public class ExpandEntityLeaveLevelEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityLeaveLevelEvent> BUS = IEventBus.direct(
            EntityLeaveLevelEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(EntityLeaveLevelEvent internal) {
        
        return internal.getLevel();
    }
    
}
