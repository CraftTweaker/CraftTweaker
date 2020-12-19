package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.DocumentConversionRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.element.KnownElementList;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods.KnownModList;

import javax.lang.model.element.TypeElement;
import java.util.Collection;

public class ElementConverter {
    
    private final DocumentConversionRegistry conversionRegistry;
    
    public ElementConverter(KnownModList knownModList, DocumentRegistry documentRegistry, CommentConverter commentConverter, TypeConverter typeConverter) {
        this.conversionRegistry = new DocumentConversionRegistry(knownModList, documentRegistry, commentConverter, typeConverter);
    }
    
    public void handleElements(KnownElementList knownElementList) {
        handleTypeDocumentation(knownElementList);
        handleExpansionDocumentation(knownElementList);
    }
    
    private void handleTypeDocumentation(KnownElementList knownElementList) {
        final Collection<TypeElement> elementsForExpansionDocumentation = knownElementList.getElementsForTypeDocumentation();
        prepareDocumentation(elementsForExpansionDocumentation);
        handleDocumentation(elementsForExpansionDocumentation);
    }
    
    private void handleExpansionDocumentation(KnownElementList knownElementList) {
        final Collection<TypeElement> elementsForExpansionDocumentation = knownElementList.getElementsForExpansionDocumentation();
        prepareDocumentation(elementsForExpansionDocumentation);
        handleDocumentation(elementsForExpansionDocumentation);
    }
    
    private void prepareDocumentation(Collection<TypeElement> elementsForExpansionDocumentation) {
        for(TypeElement typeElement : elementsForExpansionDocumentation) {
            conversionRegistry.prepareForConversion(typeElement);
        }
    }
    
    private void handleDocumentation(Collection<TypeElement> elementsForExpansionDocumentation) {
        for(TypeElement typeElement : elementsForExpansionDocumentation) {
            conversionRegistry.convert(typeElement);
        }
    }
    
    
}
