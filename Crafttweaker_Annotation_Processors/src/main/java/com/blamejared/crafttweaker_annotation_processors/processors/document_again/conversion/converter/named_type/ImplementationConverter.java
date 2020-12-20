package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImplementationConverter {
    private final TypeConverter typeConverter;
    
    public ImplementationConverter(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }
    
    public List<AbstractTypeInfo> convertInterfacesFor(TypeElement element) {
        return element.getInterfaces()
                .stream()
                .map(this::convertInterface)
                .collect(Collectors.toList());
    }
    
    private AbstractTypeInfo convertInterface(TypeMirror typeMirror) {
        return typeConverter.convertType(typeMirror);
    }
}
