package com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.OnlyIfParameterHit;
import com.blamejared.crafttweaker.platform.Services;

public class OnlyIfParameterModLoader extends OnlyIfParameter {
    
    public OnlyIfParameterModLoader() {
        
        super("modloader");
    }
    
    @Override
    public OnlyIfParameterHit isHit(String[] additionalArguments) {
        
        if(additionalArguments.length != 1) {
            return OnlyIfParameterHit.invalid();
        }
        String modLoader = additionalArguments[0];
        
    
        final boolean conditionMet = Services.PLATFORM.getPlatformName().equalsIgnoreCase(modLoader);
        if(conditionMet) {
            return OnlyIfParameterHit.conditionPassed(additionalArguments.length);
        } else {
            return OnlyIfParameterHit.conditionFailed(additionalArguments.length);
        }
    }
    
}
