package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.common.capabilities.CapabilityProvider;

@ZenRegister
@Document("forge/api/capability/CapabilityProvider")
@NativeTypeRegistration(value = CapabilityProvider.class, zenCodeName = "crafttweaker.api.capability.CapabilityProvider")
public class ExpandCapabilityProvider {

}
