package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.mixin.common.access.server.AccessReloadableServerResources;
import com.blamejared.crafttweaker.platform.helper.IAccessibleServerElementsProvider;
import com.google.common.base.Suppliers;
import net.minecraft.server.ReloadableServerResources;

import java.util.Objects;
import java.util.function.Supplier;

public final class AccessibleServerElementsProvider implements IAccessibleServerElementsProvider {
    
    private static final Supplier<AccessibleServerElementsProvider> INSTANCE = Suppliers.memoize(AccessibleServerElementsProvider::new);
    
    private ReloadableServerResources resources;
    
    private AccessibleServerElementsProvider() {
        
        this.resources = null;
    }
    
    public static IAccessibleServerElementsProvider get() {
        
        return INSTANCE.get();
    }
    
    @Override
    public ReloadableServerResources resources() {
        
        return Objects.requireNonNull(this.resources, "Resources is unavailable");
    }
    
    @Override
    public AccessReloadableServerResources accessibleResources() {
        
        return (AccessReloadableServerResources) this.resources();
    }
    
    @Override
    public void resources(final ReloadableServerResources resources) {
        
        this.resources = resources;
    }
    
}
