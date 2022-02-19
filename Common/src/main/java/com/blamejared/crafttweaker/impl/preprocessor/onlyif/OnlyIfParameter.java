package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

public abstract class OnlyIfParameter {
    
    private final String name;
    
    public OnlyIfParameter(String name) {
        
        this.name = name;
    }
    
    public String getName() {
        
        return name;
    }
    
    public abstract OnlyIfParameterHit isHit(String[] additionalArguments);
    
}
