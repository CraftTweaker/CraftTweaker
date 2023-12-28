package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/capability/ICapabilityProvider")
@NativeTypeRegistration(value = ICapabilityProvider.class, zenCodeName = "crafttweaker.api.capability.ICapabilityProvider")
public class ExpandICapabilityProvider {
    
    /**
     * Gets the capability for the given side.
     *
     * @param cap  The capability to get.
     * @param side The side to get the capability from, can be null.
     * @param <T>  The type of the capability to get.
     *
     * @return The found capability or null.
     *
     * @docParam clazz The type of the capability.
     * @docParam cap Capabilities.ENERGY
     * @docParam side <constant:minecraft:direction:north>
     */
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T getCapability(ICapabilityProvider internal, Class<T> clazz, Capability<T> cap, @ZenCodeType.Nullable Direction side) {
        
        return internal.getCapability(cap, side).resolve().orElse(null);
    }
    
    /**
     * Gets the capability.
     *
     * @param cap The capability to get.
     * @param <T> The type of the capability to get.
     *
     * @return The found capability or null.
     *
     * @docParam clazz The type of the capability.
     * @docParam cap Capabilities.ENERGY
     */
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T getCapability(ICapabilityProvider internal, Class<T> clazz, Capability<T> cap) {
        
        return internal.getCapability(cap).resolve().orElse(null);
    }
    
}
