package com.blamejared.crafttweaker.natives.event.entity.living.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/effect/MobEffectAddedEvent")
@NativeTypeRegistration(value = MobEffectEvent.Added.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.effect.MobEffectAddedEvent")
public class ExpandMobEffectAddedEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<MobEffectEvent.Added> BUS = IEventBus.direct(
            MobEffectEvent.Added.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("effectInstance")
    public static MobEffectInstance getEffectInstance(MobEffectEvent.Added internal) {
        
        return internal.getEffectInstance();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("oldEffectInstance")
    public static MobEffectInstance getOldEffectInstance(MobEffectEvent.Added internal) {
        
        return internal.getOldEffectInstance();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("effectSource")
    public static Entity getEffectSource(MobEffectEvent.Added internal) {
        
        return internal.getEffectSource();
    }
    
}
