package com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameterHit;
import com.blamejared.crafttweaker.platform.Services;

import java.util.Arrays;

public class OnlyIfParameterModNotLoaded extends OnlyIfParameter {
    
    public OnlyIfParameterModNotLoaded() {
        
        super("modnotloaded");
    }
    
    @Override
    public OnlyIfParameterHit isHit(String[] additionalArguments) {
        
        if(additionalArguments.length == 0) {
            return OnlyIfParameterHit.invalid();
        }
        
        final boolean conditionMet = Arrays.stream(additionalArguments).noneMatch(Services.PLATFORM::isModLoaded);
        if(conditionMet) {
            return OnlyIfParameterHit.conditionPassed(additionalArguments.length);
        } else {
            return OnlyIfParameterHit.conditionFailed(additionalArguments.length);
        }
    }
    
}
