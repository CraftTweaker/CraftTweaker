package com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif.OnlyIfParameterHit;

public class OnlyIfParameterTrue extends OnlyIfParameter {
    
    public OnlyIfParameterTrue() {
        
        super("true");
    }
    
    @Override
    public OnlyIfParameterHit isHit(String[] additionalArguments) {
        
        return OnlyIfParameterHit.conditionPassed(0);
    }
    
}
