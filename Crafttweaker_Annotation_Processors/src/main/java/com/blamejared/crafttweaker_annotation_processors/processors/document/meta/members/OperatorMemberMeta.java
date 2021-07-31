package com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.BaseMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.TypePageMeta;

public class OperatorMemberMeta extends BaseMeta {
    
    private String operatorType;
    private TypePageMeta returnType;
    private TypePageMeta paramType;
    
    public OperatorMemberMeta() {
    
    }
    
    public String getOperatorType() {
        
        return operatorType;
    }
    
    public void setOperatorType(String operatorType) {
        
        this.operatorType = operatorType;
    }
    
    public TypePageMeta getReturnType() {
        
        return returnType;
    }
    
    public void setReturnType(TypePageMeta returnType) {
        
        this.returnType = returnType;
    }
    
    public TypePageMeta getParamType() {
        
        return paramType;
    }
    
    public void setParamType(TypePageMeta paramType) {
        
        this.paramType = paramType;
    }
    
    
}
