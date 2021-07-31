package com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.BaseMeta;

import java.util.Set;

public class MethodMemberMeta extends BaseMeta {
    
    private String name;
    private Set<MethodParameterMeta> parameters;
    private boolean isStatic;
    
    public MethodMemberMeta() {
    
    }
    
    public String getName() {
        
        return name;
    }
    
    public void setName(String name) {
        
        this.name = name;
    }
    
    public Set<MethodParameterMeta> getParameters() {
        
        return parameters;
    }
    
    public void setParameters(Set<MethodParameterMeta> parameters) {
        
        if(parameters == null || parameters.isEmpty()) {
            return;
        }
        
        this.parameters = parameters;
    }
    
    public boolean isStatic() {
        
        return isStatic;
    }
    
    public void setStatic(boolean aStatic) {
        
        isStatic = aStatic;
    }
    
}
