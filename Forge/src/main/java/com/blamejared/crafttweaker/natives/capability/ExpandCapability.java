package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.common.capabilities.Capability;
import org.openzen.zencode.java.ZenCodeType;


@ZenRegister
@Document("forge/api/capability/Capability")
@NativeTypeRegistration(value = Capability.class, zenCodeName = "crafttweaker.api.capability.Capability")
public class ExpandCapability {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(Capability internal) {
        
        return internal.getName();
    }
    
}
