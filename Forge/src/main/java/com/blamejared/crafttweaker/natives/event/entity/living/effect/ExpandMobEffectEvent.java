package com.blamejared.crafttweaker.natives.event.entity.living.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/living/effect/MobEffectEvent")
@NativeTypeRegistration(value = MobEffectEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.effect.MobEffectEvent")
public class ExpandMobEffectEvent {
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("effectInstance")
    public static MobEffectInstance getEffectInstance(MobEffectEvent internal) {
        
        return internal.getEffectInstance();
    }
    
}
