package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules.generic;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.MapTypeInfo;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.*;

public class MapConversionRule extends AbstractGenericTypeConversionRule {
    
    
    public MapConversionRule(TypeConverter typeConverter, Types typeUtils) {
        super(typeConverter, typeUtils);
    }
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        return isMapType(mirror);
    }
    
    private boolean isMapType(TypeMirror mirror) {
        return isGenericType(mirror) && isBaseTypeOfClass(mirror, Map.class);
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        final Optional<List<AbstractTypeInfo>> typeArguments = convertTypeArguments(mirror);
        return typeArguments.map(this::getMapTypeInfo).orElse(null);
    
    }
    
    @NotNull
    private MapTypeInfo getMapTypeInfo(List<AbstractTypeInfo> typeArguments) {
        final AbstractTypeInfo keyType = getKeyTypeFrom(typeArguments);
        final AbstractTypeInfo valueType = getValueTypeFrom(typeArguments);
        
        return new MapTypeInfo(keyType, valueType);
    }
    
    private AbstractTypeInfo getKeyTypeFrom(List<AbstractTypeInfo> typeArguments) {
        return typeArguments.get(0);
    }
    
    private AbstractTypeInfo getValueTypeFrom(List<AbstractTypeInfo> typeArguments) {
        return typeArguments.get(1);
    }
}
