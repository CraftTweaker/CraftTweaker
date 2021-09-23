package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.LinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.LinkConverter;

import javax.lang.model.element.Element;
import java.util.List;
import java.util.Optional;

public class ImportedTypeLinkConversionRule implements LinkConversionRule {
    
    private final LinkConverter linkConverter;
    private final AccessibleTypesFinder accessibleTypesFinder;
    
    public ImportedTypeLinkConversionRule(LinkConverter linkConverter, AccessibleTypesFinder accessibleTypesFinder) {
        
        this.linkConverter = linkConverter;
        this.accessibleTypesFinder = accessibleTypesFinder;
    }
    
    
    @Override
    public boolean canConvert(String link) {
        
        return true;
    }
    
    @Override
    public Optional<String> tryConvertToClickableMarkdown(String link, Element element) {
        
        try {
            final String importedTypeQualifiedName = getQualifiedNameFor(link, element);
            
            final String clickableMarkdown = linkConverter.convertLinkToClickableMarkdown(importedTypeQualifiedName, element);
            return Optional.ofNullable(clickableMarkdown);
        } catch(Exception ignored) {
            return Optional.empty();
        }
    }
    
    private String getQualifiedNameFor(String link, Element element) {
        
        final List<String> importedTypes = getImportedTypesAt(element);
        return qualifyName(link, importedTypes);
    }
    
    private List<String> getImportedTypesAt(Element element) {
        
        return accessibleTypesFinder.getAllAccessibleTypesAt(element);
    }
    
    private String qualifyName(String link, List<String> importedTypes) {
        
        final String[] split = link.split("\\.", 2);
        
        for(String importedType : importedTypes) {
            if(doesImportMatchLink(importedType, split[0])) {
                return split.length == 1 ? importedType : (String.format("%s.%s", importedType, split[1]));
            }
        }
        throw new IllegalArgumentException("Could not qualify " + link);
    }
    
    private boolean doesImportMatchLink(String importedType, String link) {
        
        return importedType.endsWith("." + link);
    }
    
}
