package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type;

public class PrimitiveTypeInfo extends AbstractTypeInfo {
    
    private final String name;
    
    public PrimitiveTypeInfo(String name) {
        this.name = name;
    }
    
    @Override
    public String getDisplayName() {
        return name;
    }
    
    @Override
    public String getClickableMarkdown() {
        return getDisplayName();
    }
}
