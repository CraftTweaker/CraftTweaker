package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.generic;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.GenericTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Optional;

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
        
        final Optional<AbstractTypeInfo> baseClass = convertBaseClass(mirror);
        final Optional<List<AbstractTypeInfo>> typeArguments = convertTypeArguments(mirror);
        if(!baseClass.isPresent() || !typeArguments.isPresent()) {
            return null;
        }
        
        return new GenericTypeInfo(baseClass.get(), typeArguments.get());
    }
    
}
