package com.blamejared.crafttweaker.natives.event.entity.living.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.MobEffectEvent;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/effect/MobEffectApplicableEvent")
@NativeTypeRegistration(value = MobEffectEvent.Applicable.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.effect.MobEffectApplicableEvent")
public class ExpandMobEffectApplicableEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<MobEffectEvent.Applicable> BUS = IEventBus.direct(
            MobEffectEvent.Applicable.class,
            ForgeEventBusWire.of()
    );
    
}
