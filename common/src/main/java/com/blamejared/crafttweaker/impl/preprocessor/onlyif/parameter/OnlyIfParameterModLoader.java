package com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameterHit;
import com.blamejared.crafttweaker.platform.Services;

public final class OnlyIfParameterModLoader extends OnlyIfParameter {
    
    public OnlyIfParameterModLoader() {
        
        super("modloader");
    }
    
    @Override
    public OnlyIfParameterHit isHit(final String[] additionalArguments) {
        
        if(additionalArguments.length != 1) {
            return OnlyIfParameterHit.invalid();
        }
        
        final String modLoader = additionalArguments[0];
        final boolean conditionMet = Services.PLATFORM.getPlatformName().equalsIgnoreCase(modLoader);
        return OnlyIfParameterHit.basedOn(conditionMet, additionalArguments.length);
    }
    
}
