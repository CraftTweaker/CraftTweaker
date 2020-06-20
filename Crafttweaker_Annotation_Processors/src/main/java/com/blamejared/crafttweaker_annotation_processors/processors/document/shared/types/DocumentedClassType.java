package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types;

import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.*;

import java.util.*;

public class DocumentedClassType extends DocumentedType {
    
    private final DocumentedClass documentedClass;
    
    public DocumentedClassType(DocumentedClass documentedClass) {
        this.documentedClass = documentedClass;
    }
    
    public DocumentedClass getDocumentedClass() {
        return documentedClass;
    }
    
    @Override
    public String getZSName() {
        return documentedClass.getZSName();
    }
    
    @Override
    public String getClickableMarkdown() {
        //TODO implement.
        return String.format(Locale.ENGLISH, "[%s](/%s)", documentedClass.getZSName(), documentedClass
                .getDocPath());
    }
    
    @Override
    public String getClickableMarkdown(String member) {
        final String anchor = member.toLowerCase(Locale.ENGLISH).split("[(]", 2)[0];
        return String.format(Locale.ENGLISH, "[%s#%s](/%s/#%s)", documentedClass.getZSName(), member, documentedClass
                .getDocPath(), anchor);
    }
    
    @Override
    public String getZSShortName() {
        return this.documentedClass.getZSShortName();
    }
    
    @Override
    public String getDocParamThis() {
        return documentedClass.getDocParamThis();
    }
    
}
