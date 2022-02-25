package com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameterHit;
import com.blamejared.crafttweaker.platform.Services;

import java.util.Arrays;

public final class OnlyIfParameterModLoaded extends OnlyIfParameter {
    
    public OnlyIfParameterModLoaded() {
        
        super("modloaded");
    }
    
    @Override
    public OnlyIfParameterHit isHit(final String[] additionalArguments) {
        
        if(additionalArguments.length == 0) {
            return OnlyIfParameterHit.invalid();
        }
        
        final boolean conditionMet = Arrays.stream(additionalArguments).allMatch(Services.PLATFORM::isModLoaded);
        return OnlyIfParameterHit.basedOn(conditionMet, additionalArguments.length);
    }
    
}
