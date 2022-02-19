package com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameterHit;

public class OnlyIfParameterFalse extends OnlyIfParameter {
    
    public OnlyIfParameterFalse() {
        
        super("false");
    }
    
    @Override
    public OnlyIfParameterHit isHit(String[] additionalArguments) {
        
        return OnlyIfParameterHit.conditionFailed(0);
    }
    
}
