package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/Targeting")
@NativeTypeRegistration(value = Targeting.class, zenCodeName = "crafttweaker.api.entity.Targeting")
public class ExpandTargeting {
    
    /**
     * Gets the current target, or null if not targeting anything.
     *
     * @return The current target
     */
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("target")
    public static LivingEntity getTarget(Targeting internal) {
        
        return internal.getTarget();
    }
    
}
