package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/living/LivingEvent")
@NativeTypeRegistration(value = LivingEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingEvent")
public class ExpandLivingEvent {
    
    @ZenCodeType.Getter("entity")
    public static LivingEntity getEntity(LivingEvent internal) {
        
        return internal.getEntity();
    }
    
}
