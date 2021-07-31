package com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.BaseMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.TypePageMeta;

public class PropertyMemberMeta extends BaseMeta {
    
    private String name;
    private TypePageMeta type;
    private boolean hasGetter;
    private boolean hasSetter;
    private boolean isStatic;
    
    public PropertyMemberMeta() {
    
    }
    
    public String getName() {
        
        return name;
    }
    
    public void setName(String name) {
        
        this.name = name;
    }
    
    public TypePageMeta getType() {
        
        return type;
    }
    
    public void setType(TypePageMeta type) {
        
        this.type = type;
    }
    
    
    public boolean isHasGetter() {
        
        return hasGetter;
    }
    
    public void setHasGetter(boolean hasGetter) {
        
        this.hasGetter = hasGetter;
    }
    
    public boolean isHasSetter() {
        
        return hasSetter;
    }
    
    public void setHasSetter(boolean hasSetter) {
        
        this.hasSetter = hasSetter;
    }
    
    public boolean isStatic() {
        
        return isStatic;
    }
    
    public void setStatic(boolean aStatic) {
        
        isStatic = aStatic;
    }
    
}
