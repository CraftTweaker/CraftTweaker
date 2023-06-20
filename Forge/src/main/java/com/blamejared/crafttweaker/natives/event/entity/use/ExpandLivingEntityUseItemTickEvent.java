package com.blamejared.crafttweaker.natives.event.entity.use;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/use/LivingEntityUseItemTickEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.Tick.class, zenCodeName = "crafttweaker.forge.api.event.entity.use.LivingEntityUseItemTickEvent")
public class ExpandLivingEntityUseItemTickEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingEntityUseItemEvent.Tick> BUS = IEventBus.cancelable(
            LivingEntityUseItemEvent.Tick.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
}
