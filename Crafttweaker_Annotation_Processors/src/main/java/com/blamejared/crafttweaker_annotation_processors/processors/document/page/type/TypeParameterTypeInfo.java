package com.blamejared.crafttweaker_annotation_processors.processors.document.page.type;

public class TypeParameterTypeInfo extends AbstractTypeInfo {
    
    private final String typeParameterName;
    
    public TypeParameterTypeInfo(String typeParameterName) {
        this.typeParameterName = typeParameterName;
    }
    
    @Override
    public String getDisplayName() {
        return typeParameterName;
    }
    
    @Override
    public String getClickableMarkdown() {
        return getDisplayName();
    }
}
