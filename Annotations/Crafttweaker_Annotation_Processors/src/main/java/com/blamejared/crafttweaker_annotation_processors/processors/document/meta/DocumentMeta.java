package com.blamejared.crafttweaker_annotation_processors.processors.document.meta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DocumentMeta {
    
    private String path;
    private String ownerModId;
    private String zenCodeName;
    private String since;
    private String deprecation;
    private String shortDescription;
    private Set<String> searchTerms;
    
    public DocumentMeta() {
    
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
    
    public String getShortDescription() {
        
        return shortDescription;
    }
    
    public void setShortDescription(String shortDescription) {
        
        if(shortDescription == null || shortDescription.isEmpty()) {
            return;
        }
        
        this.shortDescription = shortDescription;
    }
    
    public String getPath() {
        
        return path;
    }
    
    public void setPath(String path) {
        
        this.path = path;
    }
    
    public String getOwnerModId() {
        
        return ownerModId;
    }
    
    public void setOwnerModId(String ownerModId) {
        
        this.ownerModId = ownerModId;
    }
    
    public String getZenCodeName() {
        
        return zenCodeName;
    }
    
    public void setZenCodeName(String zenCodeName) {
        
        this.zenCodeName = zenCodeName;
    }
    
    public Set<String> getSearchTerms() {
        
        return searchTerms;
    }
    
    public void addSearchTerms(String... term) {
        
        if(this.searchTerms == null) {
            this.searchTerms = new HashSet<>();
        }
        this.searchTerms.addAll(Arrays.asList(term));
    }
    
    
}
