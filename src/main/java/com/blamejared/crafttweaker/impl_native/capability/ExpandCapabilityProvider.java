package com.blamejared.crafttweaker.impl_native.capability;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.common.capabilities.CapabilityProvider;

@ZenRegister
@Document("vanilla/api/capability/CapabilityProvider")
@NativeTypeRegistration(value = CapabilityProvider.class, zenCodeName = "crafttweaker.api.capability.CapabilityProvider")
public class ExpandCapabilityProvider {

}
