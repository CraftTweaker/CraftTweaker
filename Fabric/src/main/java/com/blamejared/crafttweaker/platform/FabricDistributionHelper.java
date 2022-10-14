package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.platform.services.IDistributionHelper;
import com.blamejared.crafttweaker.platform.sides.DistributionType;
import net.fabricmc.loader.api.FabricLoader;

public class FabricDistributionHelper implements IDistributionHelper {
    
    @Override
    public DistributionType getDistributionType() {
        
        return DistributionType.from(FabricLoader.getInstance().getEnvironmentType());
    }
    
}
