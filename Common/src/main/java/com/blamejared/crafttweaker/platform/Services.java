package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.platform.services.IClientHelper;
import com.blamejared.crafttweaker.platform.services.IDistributionHelper;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import com.blamejared.crafttweaker.platform.services.INetworkHelper;
import com.blamejared.crafttweaker.platform.services.IPlatformHelper;
import com.blamejared.crafttweaker.platform.services.IRegistryHelper;

import java.util.ServiceLoader;

public class Services {
    
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IDistributionHelper DISTRIBUTION = load(IDistributionHelper.class);
    public static final IClientHelper CLIENT = load(IClientHelper.class);
    public static final IEventHelper EVENT = load(IEventHelper.class);
    public static final INetworkHelper NETWORK = load(INetworkHelper.class);
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);
    
    
    public static <T> T load(Class<T> clazz) {
        
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        CraftTweakerCommon.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
    
}
