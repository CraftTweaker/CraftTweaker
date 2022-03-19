package com.blamejared.crafttweaker.platform.helper;

import com.blamejared.crafttweaker.mixin.common.access.server.AccessReloadableServerResources;
import net.minecraft.server.ReloadableServerResources;

// TODO("Better package")
public interface IAccessibleServerElementsProvider {
    
    ReloadableServerResources resources();
    
    AccessReloadableServerResources accessibleResources();
    
    void resources(final ReloadableServerResources resources);
    
}
