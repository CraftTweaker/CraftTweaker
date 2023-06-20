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
@Document("forge/api/event/entity/use/LivingEntityUseItemStartEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.Start.class, zenCodeName = "crafttweaker.forge.api.event.entity.use.LivingEntityUseItemStartEvent")
public class ExpandLivingEntityUseItemStartEvent {
    @ZenEvent.Bus
    public static final IEventBus<LivingEntityUseItemEvent.Start> BUS = IEventBus.cancelable(
            LivingEntityUseItemEvent.Start.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
}
