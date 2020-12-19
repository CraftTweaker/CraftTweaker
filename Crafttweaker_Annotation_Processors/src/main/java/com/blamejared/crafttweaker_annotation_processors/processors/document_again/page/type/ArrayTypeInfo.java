package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type;

public class ArrayTypeInfo extends AbstractTypeInfo {
    
    private final AbstractTypeInfo base;
    
    public ArrayTypeInfo(AbstractTypeInfo base) {
        this.base = base;
    }
    
    @Override
    public String getDisplayName() {
        return base.getDisplayName() + "[]";
    }
}
