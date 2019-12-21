package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types;

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
}
