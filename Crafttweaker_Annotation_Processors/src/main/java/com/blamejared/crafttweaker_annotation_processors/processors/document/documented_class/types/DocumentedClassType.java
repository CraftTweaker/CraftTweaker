package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types;

import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;

import java.util.Locale;

public class DocumentedClassType extends DocumentedType {
    private final DocumentedClass documentedClass;

    public DocumentedClassType(DocumentedClass documentedClass) {
        this.documentedClass = documentedClass;
    }

    @Override
    public String getZSName() {
        return documentedClass.getZSName();
    }

    @Override
    public String getClickableMarkdown() {
        //TODO implement.
        return String.format(Locale.ENGLISH, "(%s)[%s]", documentedClass.getZSName(), documentedClass.getDocPath());
    }
}
