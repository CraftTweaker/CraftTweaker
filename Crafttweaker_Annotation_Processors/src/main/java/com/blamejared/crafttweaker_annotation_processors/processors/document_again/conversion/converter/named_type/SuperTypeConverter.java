package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;

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
        return element.getSuperclass().getKind() != TypeKind.NONE;
    }
}
