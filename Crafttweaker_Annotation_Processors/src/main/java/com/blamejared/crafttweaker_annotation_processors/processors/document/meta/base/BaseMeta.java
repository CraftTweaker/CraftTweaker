package com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base;

public class BaseMeta {
    
    private String since;
    private String deprecation;
    private String description;
    
    public BaseMeta() {
    
    }
    
    public String getSince() {
        
        return since;
    }
    
    public void setSince(String since) {
        
        if(since == null || since.isEmpty()) {
            return;
        }
        this.since = since;
    }
    
    public String getDeprecation() {
        
        return deprecation;
    }
    
    public void setDeprecation(String deprecation) {
        
        if(deprecation == null || deprecation.isEmpty()) {
            return;
        }
        
        this.deprecation = deprecation;
        
    }
    
    public String getDescription() {
        
        return description;
    }
    
    public void setDescription(String description) {
        
        if(description == null || description.isEmpty()) {
            return;
        }
        
        this.description = description;
    }
    
}
