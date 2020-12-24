package com.blamejared.crafttweaker.impl_native.event.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/entity/MCLivingEvent")
@NativeTypeRegistration(value = LivingEvent.class, zenCodeName = "crafttweaker.api.event.entity.MCLivingEvent")
public class ExpandLivingEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entityLiving")
    public static LivingEntity getEntityLiving(LivingEvent internal) {
        return internal.getEntityLiving();
    }
}
