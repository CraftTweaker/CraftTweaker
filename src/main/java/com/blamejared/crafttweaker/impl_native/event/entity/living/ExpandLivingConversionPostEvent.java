package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingConversionPostEvent")
@NativeTypeRegistration(value = LivingConversionEvent.Post.class, zenCodeName = "crafttweaker.api.event.entity.living.LivingConversionPostEvent")
public class ExpandLivingConversionPostEvent {
    
    @ZenCodeType.Getter("outcome")
    public static LivingEntity getOutcome(LivingConversionEvent.Post internal) {
        
        return internal.getOutcome();
    }
    
}
