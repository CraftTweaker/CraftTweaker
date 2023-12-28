package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.platform.services.IBridgeService;
import com.blamejared.crafttweaker.platform.services.IClientHelper;
import com.blamejared.crafttweaker.platform.services.IDistributionHelper;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import com.blamejared.crafttweaker.platform.services.INetworkHelper;
import com.blamejared.crafttweaker.platform.services.IPlatformHelper;
import com.blamejared.crafttweaker.platform.services.IRegistryHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ServiceLoader;

public final class Services {
    
    // Using a custom logger is required due to otherwise circular dependency
    // (CraftTweakerCommon loads CraftTweakerAPI, which loads Services, which needs CraftTweakerCommon)
    private static final Logger LOGGER = LogManager.getLogger(CraftTweakerConstants.MOD_NAME + "-Services");
    
    public static final IBridgeService BRIDGE = load(IBridgeService.class);
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IDistributionHelper DISTRIBUTION = load(IDistributionHelper.class);
    public static final IClientHelper CLIENT = load(IClientHelper.class);
    public static final IEventHelper EVENT = load(IEventHelper.class);
    public static final INetworkHelper NETWORK = load(INetworkHelper.class);
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);
    
    private Services() {}
    
    public static <T> T load(Class<T> clazz) {
        
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
    
}
