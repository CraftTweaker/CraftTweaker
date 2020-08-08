package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping;

public class SnippingParameterNoStart extends SnippingParameter {
    
    public SnippingParameterNoStart() {
        super("nostart");
    }
    
    @Override
    public SnippingParameterHit isHit(String[] additionalArguments) {
        return SnippingParameterHit.noSnip(0);
    }
}
