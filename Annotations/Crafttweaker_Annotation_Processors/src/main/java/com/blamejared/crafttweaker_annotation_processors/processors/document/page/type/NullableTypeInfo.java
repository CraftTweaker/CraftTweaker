package com.blamejared.crafttweaker_annotation_processors.processors.document.page.type;

// TODO("All of this is just to make it work; make it work better now")
public class NullableTypeInfo extends AbstractTypeInfo {
    
    private final AbstractTypeInfo wrapped;
    
    public NullableTypeInfo(final AbstractTypeInfo wrapped) {
        
        this.wrapped = wrapped;
    }
    
    @Override
    public String getDisplayName() {
        
        return this.wrapped.getDisplayName() + "?";
    }
    
    @Override
    public String getClickableMarkdown() {
        
        return this.wrapped.getClickableMarkdown() + "?";
    }
    
}
