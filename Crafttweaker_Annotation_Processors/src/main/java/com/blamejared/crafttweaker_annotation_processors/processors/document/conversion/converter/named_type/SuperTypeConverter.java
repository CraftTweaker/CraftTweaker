package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.named_type;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Optional;

public class SuperTypeConverter {
    
    private final TypeConverter typeConverter;
    
    public SuperTypeConverter(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }
    
    public Optional<AbstractTypeInfo> convertSuperTypeFor(TypeElement element) {
        if(!hasSuperType(element)) {
            return Optional.empty();
        }
        
        return convertSuperType(element);
    }
    
    private Optional<AbstractTypeInfo> convertSuperType(TypeElement element) {
        return typeConverter.tryConvertType(element.getSuperclass());
    }
    
    private boolean hasSuperType(TypeElement element) {
        final TypeMirror superclass = element.getSuperclass();
        return !superTypeIsObject(superclass) && superclass.getKind() != TypeKind.NONE;
    }
    
    private boolean superTypeIsObject(TypeMirror superclass) {
        return superclass.toString().equals("java.lang.Object");
    }

    public TypeConverter getTypeConverter() {
        return typeConverter;
    }
}
