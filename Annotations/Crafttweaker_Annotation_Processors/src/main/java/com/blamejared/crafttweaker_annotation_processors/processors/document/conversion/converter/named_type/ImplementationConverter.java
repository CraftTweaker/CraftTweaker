package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.named_type;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Optional;
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
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
    
    private Optional<AbstractTypeInfo> convertInterface(TypeMirror typeMirror) {
        
        return typeConverter.tryConvertType(typeMirror);
    }
    
}
