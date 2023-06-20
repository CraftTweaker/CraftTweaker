package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LightningBolt;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/EntityStruckByLightningEvent")
@NativeTypeRegistration(value = EntityStruckByLightningEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.EntityStruckByLightningEvent")
public class ExpandEntityStruckByLightningEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityStruckByLightningEvent> BUS = IEventBus.cancelable(
            EntityStruckByLightningEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("lightning")
    public static LightningBolt getLightning(EntityStruckByLightningEvent internal) {
        
        return internal.getLightning();
    }
    
}
