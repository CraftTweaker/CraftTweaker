package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types;

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

    @Override
    public String getZSShortName() {
        return base.getZSShortName() + "Array";
    }
}
