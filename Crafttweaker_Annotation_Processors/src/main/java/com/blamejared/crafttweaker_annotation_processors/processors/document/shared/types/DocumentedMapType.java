package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types;

public class DocumentedMapType extends DocumentedType {
    private final DocumentedType keyType, valueType;

    public DocumentedMapType(DocumentedType keyType, DocumentedType valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    @Override
    public String getZSName() {
        return valueType.getZSName() + "[" + keyType.getZSName() + "]";
    }

    @Override
    public String getClickableMarkdown() {
        return valueType.getClickableMarkdown() + "[" + keyType.getClickableMarkdown() + "]";
    }

    @Override
    public String getZSShortName() {
        return keyType.getZSShortName() + valueType.getZSShortName() + "Map";
    }
    
    @Override
    public String getDocParamThis() {
        return "{} as " + getZSName();
    }
}
