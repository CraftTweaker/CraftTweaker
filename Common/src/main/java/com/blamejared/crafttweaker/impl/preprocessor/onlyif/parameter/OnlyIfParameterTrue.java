package com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameterHit;

public final class OnlyIfParameterTrue extends OnlyIfParameter {
    
    public OnlyIfParameterTrue() {
        
        super("true");
    }
    
    @Override
    public OnlyIfParameterHit isHit(final String[] additionalArguments) {
        
        return OnlyIfParameterHit.conditionPassed(0);
    }
    
}
