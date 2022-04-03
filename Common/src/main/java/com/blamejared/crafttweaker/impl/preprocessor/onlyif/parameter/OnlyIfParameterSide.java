package com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameterHit;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker.platform.sides.DistributionType;

public final class OnlyIfParameterSide extends OnlyIfParameter {
    
    public OnlyIfParameterSide() {
        
        super("side");
    }
    
    @Override
    public OnlyIfParameterHit isHit(final String[] additionalArguments) {
        
        if(additionalArguments.length == 0) {
            return OnlyIfParameterHit.invalid();
        }
        final String side = additionalArguments[0];
        
        final DistributionType targetDistribution;
        if(DistributionType.SERVER.matches(side)) {
            targetDistribution = DistributionType.SERVER;
        } else if(DistributionType.CLIENT.matches(side)) {
            targetDistribution = DistributionType.CLIENT;
        } else {
            throw new IllegalArgumentException("Unknown side passed into #onlyif side: " + side);
        }
        
        final boolean conditionMet = Services.DISTRIBUTION.getDistributionType() == targetDistribution;
        return OnlyIfParameterHit.basedOn(conditionMet, additionalArguments.length);
    }
    
}
