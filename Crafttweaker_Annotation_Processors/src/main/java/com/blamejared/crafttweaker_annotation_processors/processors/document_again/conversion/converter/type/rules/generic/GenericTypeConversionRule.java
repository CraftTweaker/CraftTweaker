package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules.generic;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.GenericTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;

public class GenericTypeConversionRule extends AbstractGenericTypeConversionRule {
    
    public GenericTypeConversionRule(TypeConverter typeConverter, Types typeUtils) {
        super(typeConverter, typeUtils);
    }
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        return isGenericType(mirror);
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        final AbstractTypeInfo baseClass = convertBaseClass(mirror);
        final List<AbstractTypeInfo> typeArguments = convertTypeArguments(mirror);
        return new GenericTypeInfo(baseClass, typeArguments);
    }
}
