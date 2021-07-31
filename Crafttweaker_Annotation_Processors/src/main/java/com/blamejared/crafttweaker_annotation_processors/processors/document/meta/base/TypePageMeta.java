package com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.TypePageTypeInfo;

public class TypePageMeta {
    
    private String zenCodeName;
    private String path;
    
    public TypePageMeta() {
    
    }
    
    public TypePageMeta(String zenCodeName) {
        
        this.zenCodeName = zenCodeName;
    }
    
    public TypePageMeta(AbstractTypeInfo type) {
        // TODO ideally this should return a zencode name for everything that it can, but it has issues with arrays and I'm not sure if I *need* a zencodename
//        if(type instanceof TypePageTypeInfo) {
//            this.zenCodeName = ((TypePageTypeInfo) type).getZenCodeName().getZenCodeName();
//        } else {
            this.zenCodeName = type.getDisplayName();
//        }
    }
    
    public String getZenCodeName() {
        
        return zenCodeName;
    }
    
    public void setZenCodeName(String zenCodeName) {
        
        this.zenCodeName = zenCodeName;
    }
    
    public String getPath() {
        
        return path;
    }
    
    public void setPath(String path) {
        
        this.path = path;
    }
    
}
