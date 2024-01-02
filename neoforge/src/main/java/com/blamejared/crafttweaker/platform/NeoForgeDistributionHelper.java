package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.platform.services.IDistributionHelper;
import com.blamejared.crafttweaker.platform.sides.DistributionType;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgeDistributionHelper implements IDistributionHelper {
    
    @Override
    public DistributionType getDistributionType() {
        
        return DistributionType.from(FMLLoader.getDist());
    }
    
}
