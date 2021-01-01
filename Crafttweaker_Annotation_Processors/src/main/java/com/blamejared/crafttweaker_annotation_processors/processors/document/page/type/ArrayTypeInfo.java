package com.blamejared.crafttweaker_annotation_processors.processors.document.page.type;

public class ArrayTypeInfo extends AbstractTypeInfo {
    
    private final AbstractTypeInfo base;
    
    public ArrayTypeInfo(AbstractTypeInfo base) {
        this.base = base;
    }
    
    @Override
    public String getDisplayName() {
        return base.getDisplayName() + "[]";
    }
    
    @Override
    public String getClickableMarkdown() {
        return base.getClickableMarkdown() + "[]";
    }
    
    @Override
    public String getSimpleMarkdown() {
        return base.getSimpleMarkdown() + "[]";
    }
}
