package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.platform.services.IDistributionHelper;
import com.blamejared.crafttweaker.platform.sides.DistributionType;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgeDistributionHelper implements IDistributionHelper {
    
    @Override
    public DistributionType getDistributionType() {
        
        return DistributionType.from(FMLLoader.getDist());
    }
    
}
