package com.blamejared.crafttweaker_annotation_processors.processors.document.meta;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.BaseMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.TypePageMeta;

import java.util.Set;

public class DocumentMeta extends BaseMeta {
    
    private String path;
    private String zenCodeName;
    private String ownerModId;
    private Set<TypePageMeta> implementedTypes;
    private MemberMeta members;
    private boolean exported = true;
    
    public DocumentMeta() {
    
    }
    
    public String getPath() {
        
        return path;
    }
    
    public void setPath(String path) {
        
        this.path = path;
    }
    
    public String getZenCodeName() {
        
        return zenCodeName;
    }
    
    public void setZenCodeName(String zenCodeName) {
        
        this.zenCodeName = zenCodeName;
    }
    
    public String getOwnerModId() {
        
        return ownerModId;
    }
    
    public void setOwnerModId(String ownerModId) {
        
        this.ownerModId = ownerModId;
    }
    
    public Set<TypePageMeta> getImplementedTypes() {
        
        return implementedTypes;
    }
    
    public void setImplementedTypes(Set<TypePageMeta> implementedTypes) {
        
        if(implementedTypes == null || implementedTypes.isEmpty()) {
            return;
        }
        
        this.implementedTypes = implementedTypes;
    }
    
    public MemberMeta getMembers() {
        
        return members;
    }
    
    public void setMembers(MemberMeta members) {
        
        this.members = members;
    }
    
    public boolean isExported() {
        
        return exported;
    }
    
    public void setExported(boolean exported) {
        
        this.exported = exported;
    }
    
}
