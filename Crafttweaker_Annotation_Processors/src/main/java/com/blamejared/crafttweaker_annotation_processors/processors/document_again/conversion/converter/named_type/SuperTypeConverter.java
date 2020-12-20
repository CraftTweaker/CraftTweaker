package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class SuperTypeConverter {
    
    private final TypeConverter typeConverter;
    
    public SuperTypeConverter(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }
    
    @Nullable
    public AbstractTypeInfo convertSuperTypeFor(TypeElement element) {
        if(!hasSuperType(element)) {
            return null;
        }
        
        return convertSuperType(element);
    }
    
    private AbstractTypeInfo convertSuperType(TypeElement element) {
        return typeConverter.convertType(element.getSuperclass());
    }
    
    private boolean hasSuperType(TypeElement element) {
        final TypeMirror superclass = element.getSuperclass();
        return !superTypeIsObject(superclass) && superclass.getKind() != TypeKind.NONE;
    }
    
    private boolean superTypeIsObject(TypeMirror superclass) {
        return superclass.toString().equals("java.lang.Object");
    }
}
