package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.platform.helper.IAccessibleClientElementsProvider;
import com.google.common.base.Suppliers;
import net.minecraft.core.RegistryAccess;

import java.util.Objects;
import java.util.function.Supplier;

public final class AccessibleClientElementsProvider implements IAccessibleClientElementsProvider {
    
    private static final Supplier<AccessibleClientElementsProvider> INSTANCE = Suppliers.memoize(AccessibleClientElementsProvider::new);
    
    private RegistryAccess registryAccess;
    
    private AccessibleClientElementsProvider() {
        
        this.registryAccess = null;
    }
    
    static IAccessibleClientElementsProvider get() {
        
        return INSTANCE.get();
    }
    
    @Override
    public RegistryAccess registryAccess() {
        
        return Objects.requireNonNull(this.registryAccess, "RegistryAccess is unavailable");
    }
    
    @Override
    public void registryAccess(final RegistryAccess registryAccess) {
        
        this.registryAccess = registryAccess;
    }
    
    @Override
    public boolean hasRegistryAccess() {
        
        return this.registryAccess != null;
    }
    
}
