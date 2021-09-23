package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping;

public abstract class SnippingParameter {
    
    private final String name;
    
    public SnippingParameter(String name) {
        
        this.name = name;
    }
    
    public String getName() {
        
        return name;
    }
    
    public abstract SnippingParameterHit isHit(String[] additionalArguments);
    
}
