package com.blamejared.crafttweaker_annotation_processors.processors.document.meta;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.BaseMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.CasterMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.MethodMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.OperatorMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.PropertyMemberMeta;

import java.util.Set;

public class MemberMeta extends BaseMeta {
    
    private Set<CasterMemberMeta> casters;
    private Set<OperatorMemberMeta> operators;
    private Set<MethodMemberMeta> methods;
    private Set<PropertyMemberMeta> properties;
    
    public MemberMeta() {
    
    }
    
    public Set<CasterMemberMeta> getCasters() {
        
        return casters;
    }
    
    public void setCasters(Set<CasterMemberMeta> casters) {
        
        if(casters == null || casters.isEmpty()) {
            return;
        }
        
        this.casters = casters;
    }
    
    public Set<OperatorMemberMeta> getOperators() {
        
        return operators;
    }
    
    public void setOperators(Set<OperatorMemberMeta> operators) {
        
        if(operators == null || operators.isEmpty()) {
            return;
        }
        
        this.operators = operators;
    }
    
    public Set<MethodMemberMeta> getMethods() {
        
        return methods;
    }
    
    public void setMethods(Set<MethodMemberMeta> methods) {
        
        if(methods == null || methods.isEmpty()) {
            return;
        }
        
        this.methods = methods;
    }
    
    public Set<PropertyMemberMeta> getProperties() {
        
        return properties;
    }
    
    public void setProperties(Set<PropertyMemberMeta> properties) {
        
        if(properties == null || properties.isEmpty()) {
            return;
        }
        
        this.properties = properties;
    }
    
}
