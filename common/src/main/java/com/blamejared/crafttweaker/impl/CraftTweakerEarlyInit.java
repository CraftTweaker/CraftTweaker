package com.blamejared.crafttweaker.impl;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.logging.CraftTweakerLog4jEditor;
import com.blamejared.crafttweaker.impl.script.recipefs.RecipeFileSystemProviderInjector;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public final class CraftTweakerEarlyInit {
    
    private static final String EARLY_INIT_SYSTEM = CraftTweakerConstants.MOD_NAME + "-EarlyInit";
    
    // This needs to be a supplier because we can only obtain the logger after CraftTweakerLog4jEditor has returned
    private static final Supplier<Logger> LOGGER = Suppliers.memoize(() -> CraftTweakerAPI.getLogger(EARLY_INIT_SYSTEM));
    
    private CraftTweakerEarlyInit() {}
    
    public static void run() {
        
        CraftTweakerLog4jEditor.edit();
        announce();
        RecipeFileSystemProviderInjector.inject(LOGGER.get());
    }
    
    private static void announce() {
        
        LOGGER.get().info(
                "{} is running on a Minecraft {} {} {} modded with platform {}",
                CraftTweakerConstants.MOD_NAME,
                SharedConstants.getCurrentVersion().getName(),
                Services.PLATFORM.isDevelopmentEnvironment() ? "development" : "production",
                Services.DISTRIBUTION.getDistributionType(),
                Services.PLATFORM.getPlatformName()
        );
    }
    
}
