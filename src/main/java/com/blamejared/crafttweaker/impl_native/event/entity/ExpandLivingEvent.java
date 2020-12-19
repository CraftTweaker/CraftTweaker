package com.blamejared.crafttweaker.impl_native.event.entity;

import com.blamejared.crafttweaker_annotations.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.DocumentAsType;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@DocumentAsType
@NativeExpansion(LivingEvent.class)
public class ExpandLivingEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entityLiving")
    public static LivingEntity getEntityLiving(LivingEvent internal) {
        return internal.getEntityLiving();
    }
}
