package com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.TypePageMeta;

public class MethodParameterMeta  {
    
    private String name;
    private boolean optional;
    private TypePageMeta type;
    
    public MethodParameterMeta() {
    
    }
    
    public String getName() {
        
        return name;
    }
    
    public void setName(String name) {
        
        this.name = name;
    }
    
    public boolean isOptional() {
        
        return optional;
    }
    
    public void setOptional(boolean optional) {
        
        this.optional = optional;
    }
    
    public TypePageMeta getType() {
        
        return type;
    }
    
    public void setType(TypePageMeta type) {
        
        this.type = type;
    }
    
}
