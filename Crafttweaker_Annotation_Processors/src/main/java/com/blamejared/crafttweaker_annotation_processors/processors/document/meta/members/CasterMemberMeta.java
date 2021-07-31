package com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.BaseMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.TypePageMeta;

public class CasterMemberMeta extends BaseMeta {
    
    private TypePageMeta returnType;
    private boolean implicit;
    
    public CasterMemberMeta() {
    
    }
    
    public TypePageMeta getReturnType() {
        
        return returnType;
    }
    
    public void setReturnType(TypePageMeta returnType) {
        
        this.returnType = returnType;
    }
    
    public boolean isImplicit() {
        
        return implicit;
    }
    
    public void setImplicit(boolean implicit) {
        
        this.implicit = implicit;
    }
    
}
