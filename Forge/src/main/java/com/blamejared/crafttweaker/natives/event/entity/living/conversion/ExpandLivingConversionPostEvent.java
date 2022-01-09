package com.blamejared.crafttweaker.natives.event.entity.living.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/living/conversion/LivingConversionPostEvent")
@NativeTypeRegistration(value = LivingConversionEvent.Post.class, zenCodeName = "crafttweaker.api.event.entity.living.conversion.LivingConversionPostEvent")
public class ExpandLivingConversionPostEvent {
    
    @ZenCodeType.Getter("outcome")
    public static LivingEntity getOutcome(LivingConversionEvent.Post internal) {
        
        return internal.getOutcome();
    }
    
}
