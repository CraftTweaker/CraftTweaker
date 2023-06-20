package com.blamejared.crafttweaker.natives.event.entity.living.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/effect/MobEffectRemoveEvent")
@NativeTypeRegistration(value = MobEffectEvent.Remove.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.effect.MobEffectRemoveEvent")
public class ExpandMobEffectRemoveEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<MobEffectEvent.Remove> BUS = IEventBus.cancelable(
            MobEffectEvent.Remove.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    
    @ZenCodeType.Getter("effect")
    public static MobEffect getEffect(MobEffectEvent.Remove internal) {
        
        return internal.getEffect();
    }
    
}
