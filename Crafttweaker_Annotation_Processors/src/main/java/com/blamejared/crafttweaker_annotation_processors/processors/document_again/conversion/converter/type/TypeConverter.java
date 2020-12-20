package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.PrimitiveTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.TypePageTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

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
        throw new UnsupportedOperationException("TODO: " + name.getZenCodeName());
    }
    
    public AbstractTypeInfo convertType(TypeMirror typeMirror) {
        if(typeMirror.getAnnotation(ZenCodeType.Name.class) != null) {
            final ZenCodeType.Name annotation = typeMirror.getAnnotation(ZenCodeType.Name.class);
            return convertByName(new TypeName(annotation.value()));
        }
        
        //FIXME Do proper checks for primitives, generics and the like here
        return new PrimitiveTypeInfo(typeMirror.toString());
    }
}
