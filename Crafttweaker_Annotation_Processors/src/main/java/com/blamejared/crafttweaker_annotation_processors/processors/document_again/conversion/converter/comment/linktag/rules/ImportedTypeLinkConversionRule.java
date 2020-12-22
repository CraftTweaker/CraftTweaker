package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.LinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.LinkConverter;

import javax.lang.model.element.Element;
import java.util.List;

public class ImportedTypeLinkConversionRule implements LinkConversionRule {
    
    private final LinkConverter linkConverter;
    private final AccessibleTypesFinder accessibleTypesFinder;
    
    public ImportedTypeLinkConversionRule(LinkConverter linkConverter, AccessibleTypesFinder accessibleTypesFinder) {
        this.linkConverter = linkConverter;
        this.accessibleTypesFinder = accessibleTypesFinder;
    }
    
    
    @Override
    public boolean canConvert(String link) {
        return isDirectlyReferenced(link);
    }
    
    private boolean isDirectlyReferenced(String link) {
        return !link.contains(".");
    }
    
    @Override
    public String convertToClickableMarkdown(String link, Element element) {
        final String importedTypeQualifiedName = getQualifiedNameFor(link, element);
        return linkConverter.convertLinkToClickableMarkdown(importedTypeQualifiedName, element);
    }
    
    private String getQualifiedNameFor(String link, Element element) {
        final List<String> importedTypes = getImportedTypesAt(element);
        return qualifyName(link, importedTypes);
    }
    
    private List<String> getImportedTypesAt(Element element) {
        return accessibleTypesFinder.getAllAccessibleTypesAt(element);
    }
    
    private String qualifyName(String link, List<String> importedTypes) {
        for(String importedType : importedTypes) {
            if(doesImportMatchLink(importedType, link)) {
                return importedType;
            }
        }
        throw new IllegalArgumentException("Could not qualify " + link);
    }
    
    private boolean doesImportMatchLink(String importedType, String link) {
        return importedType.endsWith("." + link);
    }
}
