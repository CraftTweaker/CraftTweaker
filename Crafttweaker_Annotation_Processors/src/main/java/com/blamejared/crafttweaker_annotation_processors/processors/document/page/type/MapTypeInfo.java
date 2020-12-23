package com.blamejared.crafttweaker_annotation_processors.processors.document.page.type;

public class MapTypeInfo extends AbstractTypeInfo {
    
    private final AbstractTypeInfo keyType;
    private final AbstractTypeInfo valueType;
    
    public MapTypeInfo(AbstractTypeInfo keyType, AbstractTypeInfo valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }
    
    @Override
    public String getDisplayName() {
        return String.format("%s[%s]", valueType.getDisplayName(), keyType.getDisplayName());
    }
    
    @Override
    public String getSimpleMarkdown() {
        return String.format("%s[%s]", valueType.getSimpleMarkdown(), keyType.getSimpleMarkdown());
    }
    
    @Override
    public String getClickableMarkdown() {
        return String.format("%s[%s]", valueType.getClickableMarkdown(), keyType.getClickableMarkdown());
    }
}
