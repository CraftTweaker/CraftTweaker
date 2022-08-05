package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/LivingEvent")
@NativeTypeRegistration(value = LivingEvent.class, zenCodeName = "crafttweaker.api.event.entity.LivingEvent")
public class ExpandLivingEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entityLiving")
    public static LivingEntity getEntityLiving(LivingEvent internal) {
        
        return internal.getEntity();
    }
    
}
