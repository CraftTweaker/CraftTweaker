package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProviderImpl;

@ZenRegister
@Document("forge/api/capability/ICapabilityProviderImpl")
@NativeTypeRegistration(value = ICapabilityProviderImpl.class, zenCodeName = "crafttweaker.api.capability.ICapabilityProviderImpl")
public class ExpandICapabilityProviderImpl {

}
