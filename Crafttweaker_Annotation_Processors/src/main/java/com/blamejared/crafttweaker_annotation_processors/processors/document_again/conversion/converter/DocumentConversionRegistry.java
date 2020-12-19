package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.expansion.ExpansionConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods.KnownModList;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DocumentConversionRegistry {
    
    private final List<DocumentConverter> converters = new ArrayList<>();
    private final DocumentRegistry documentRegistry;
    
    public DocumentConversionRegistry(KnownModList knownModList, DocumentRegistry documentRegistry, CommentConverter commentConverter, TypeConverter typeConverter) {
        this.documentRegistry = documentRegistry;
        //TODO: Add converters
        converters.add(new ExpansionConverter(knownModList, commentConverter, documentRegistry, typeConverter));
    }
    
    public void convert(TypeElement typeElement) {
        converters.stream()
                .filter(converter -> converter.canConvert(typeElement))
                .map(converter -> converter.convert(typeElement, documentRegistry.getPageInfoFor(typeElement)))
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(documentRegistry::addDocumentationPage);
    }
    
    public void prepareForConversion(TypeElement typeElement) {
        converters.stream()
                .filter(converter -> converter.canConvert(typeElement))
                .map(converter -> converter.prepareConversion(typeElement))
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(pageInfo -> documentRegistry.addInfo(typeElement, pageInfo));
    }
}
