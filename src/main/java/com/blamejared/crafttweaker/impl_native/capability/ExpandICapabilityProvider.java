package com.blamejared.crafttweaker.impl_native.capability;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/capability/ICapabilityProvider")
@NativeTypeRegistration(value = ICapabilityProvider.class, zenCodeName = "crafttweaker.api.capability.ICapabilityProvider")
public class ExpandICapabilityProvider {
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T getCapability(ICapabilityProvider internal, Class<T> clazz, Capability<T> cap, @ZenCodeType.Nullable MCDirection side) {
        
        return internal.getCapability(cap, side == null ? null : side.getInternal()).resolve().orElse(null);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T getCapability(ICapabilityProvider internal, Class<T> clazz, Capability<T> cap) {
        
        return internal.getCapability(cap).resolve().orElse(null);
    }
    
}
