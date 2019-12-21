package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types;

public class DocumentedArrayType extends DocumentedType {
    private final DocumentedType base;

    public DocumentedArrayType(DocumentedType base) {
        this.base = base;
    }

    @Override
    public String getZSName() {
        return base.getZSName() + "[]";
    }

    @Override
    public String getClickableMarkdown() {
        return base.getClickableMarkdown() + "[]";
    }
}
