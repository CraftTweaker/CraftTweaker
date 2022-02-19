package com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameterHit;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker.platform.sides.DistributionType;

public class OnlyIfParameterSide extends OnlyIfParameter {
    
    public OnlyIfParameterSide() {
        
        super("side");
    }
    
    @Override
    public OnlyIfParameterHit isHit(String[] additionalArguments) {
        
        if(additionalArguments.length == 0) {
            return OnlyIfParameterHit.invalid();
        }
        String side = additionalArguments[0];
        
        DistributionType targetDistribution = null;
        if(DistributionType.SERVER.matches(side)) {
            targetDistribution = DistributionType.SERVER;
        } else if(DistributionType.CLIENT.matches(side)) {
            targetDistribution = DistributionType.CLIENT;
        } else {
            throw new IllegalArgumentException("Unknown side passed into #onlyif side: " + side);
        }
        
        
        boolean conditionMet = Services.DISTRIBUTION.getDistributionType() == targetDistribution;
        
        if(conditionMet) {
            return OnlyIfParameterHit.conditionPassed(additionalArguments.length);
        } else {
            return OnlyIfParameterHit.conditionFailed(additionalArguments.length);
        }
    }
    
}
