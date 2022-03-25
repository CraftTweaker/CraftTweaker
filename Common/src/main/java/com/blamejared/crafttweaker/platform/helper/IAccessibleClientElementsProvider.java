package com.blamejared.crafttweaker.platform.helper;

import net.minecraft.core.RegistryAccess;

// TODO("Better package")
public interface IAccessibleClientElementsProvider {
    
    RegistryAccess registryAccess();
    
    void registryAccess(final RegistryAccess registryAccess);
    
}
