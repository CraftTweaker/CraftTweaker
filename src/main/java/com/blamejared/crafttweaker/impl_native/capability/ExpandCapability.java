package com.blamejared.crafttweaker.impl_native.capability;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.common.capabilities.Capability;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/capability/Capability")
@NativeTypeRegistration(value = Capability.class, zenCodeName = "crafttweaker.api.capability.Capability")
public class ExpandCapability {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(Capability internal) {
        
        return internal.getName();
    }
    
}
