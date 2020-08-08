package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.parameters;

import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.*;

public class SnippingParameterStart extends SnippingParameter {
    
    public SnippingParameterStart() {
        super("start");
    }
    
    @Override
    public SnippingParameterHit isHit(String[] additionalArguments) {
        return SnippingParameterHit.snip(0);
    }
}
