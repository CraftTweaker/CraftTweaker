package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BaseCapability;
import org.openzen.zencode.java.ZenCodeType;


@ZenRegister
@Document("neoforge/api/capability/BaseCapability")
@NativeTypeRegistration(value = BaseCapability.class, zenCodeName = "crafttweaker.neoforge.api.capability.BaseCapability")
public class ExpandBaseCapability {
    
    @ZenCodeType.Getter("name")
    public static ResourceLocation name(BaseCapability internal) {
        
        return internal.name();
    }
    
}
