package com.blamejared.crafttweaker_annotation_processors.processors.document.page.type;

public class ExistingTypeInfo extends AbstractTypeInfo{
    
    private final String qualifiedName;
    
    public ExistingTypeInfo(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
    
    @Override
    public String getDisplayName() {
        return qualifiedName;
    }
    
    @Override
    public String getClickableMarkdown() {
        return getDisplayName();
    }
}
