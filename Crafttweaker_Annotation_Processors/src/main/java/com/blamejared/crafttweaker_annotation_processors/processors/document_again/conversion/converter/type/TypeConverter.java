package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.TypePageTypeInfo;

import javax.lang.model.type.TypeMirror;
import java.util.Optional;

public class TypeConverter {
    
    private final DocumentRegistry registry;
    
    public TypeConverter(DocumentRegistry registry) {
        this.registry = registry;
    }
    
    
    public AbstractTypeInfo convertByName(TypeName name) {
        final Optional<TypePageInfo> pageInfoByName = registry.getPageInfoByName(name);
        if(pageInfoByName.isPresent()) {
            return new TypePageTypeInfo(pageInfoByName.get());
        }
        throw new UnsupportedOperationException("TODO");
    }
    
    public AbstractTypeInfo convertType(TypeMirror typeMirror) {
        if(typeMirror.getKind().isPrimitive()) {
        
        }
        return null;
    }
}
