package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.ArrayTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class ArrayConversionRule implements TypeConversionRule {
    
    private final TypeConverter typeConverter;
    
    public ArrayConversionRule(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        return mirror.getKind() == TypeKind.ARRAY;
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        final ArrayType arrayType = (ArrayType) mirror;
        final AbstractTypeInfo baseType = convertBaseType(arrayType);
        
        return new ArrayTypeInfo(baseType);
    }
    
    private AbstractTypeInfo convertBaseType(ArrayType arrayType) {
        return typeConverter.convertType(arrayType.getComponentType());
    }
}
