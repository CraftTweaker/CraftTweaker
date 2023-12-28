package com.blamejared.crafttweaker.natives.entity.type.misc;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.ExperienceOrb;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/misc/ExperienceOrb")
@NativeTypeRegistration(value = ExperienceOrb.class, zenCodeName = "crafttweaker.api.entity.type.misc.ExperienceOrb")
public class ExpandExperienceOrb {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("value")
    public static int getValue(ExperienceOrb internal) {
        
        return internal.getValue();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("icon")
    public static int getIcon(ExperienceOrb internal) {
        
        return internal.getIcon();
    }
    
}
