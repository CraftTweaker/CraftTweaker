package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/EntityTravelToDimensionEvent")
@NativeTypeRegistration(value = EntityTravelToDimensionEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.EntityTravelToDimensionEvent")
public class ExpandEntityTravelToDimensionEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityTravelToDimensionEvent> BUS = IEventBus.cancelable(
            EntityTravelToDimensionEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("dimension")
    public static ResourceLocation getDimension(EntityTravelToDimensionEvent internal) {
        
        return internal.getDimension().location();
    }
    
}
