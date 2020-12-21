package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules.generic;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractGenericTypeConversionRule implements TypeConversionRule {
    
    protected final TypeConverter typeConverter;
    protected final Types typeUtils;
    
    public AbstractGenericTypeConversionRule(TypeConverter typeConverter, Types typeUtils) {
        this.typeConverter = typeConverter;
        this.typeUtils = typeUtils;
    }
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        return isGenericType(mirror);
    }
    
    protected boolean isGenericType(TypeMirror mirror) {
        return isDeclaredType(mirror) && isGenericMirror((DeclaredType) mirror);
        
    }
    
    private boolean isGenericMirror(DeclaredType typeElement) {
        return !typeElement.getTypeArguments().isEmpty();
    }
    
    private boolean isDeclaredType(TypeMirror mirror) {
        return mirror instanceof DeclaredType;
    }
    
    
    protected AbstractTypeInfo convertBaseClass(TypeMirror mirror) {
        final TypeMirror baseTypeMirror = getBaseType(mirror);
        return typeConverter.convertType(baseTypeMirror);
    }
    
    protected TypeMirror getBaseType(TypeMirror mirror) {
        return typeUtils.erasure(mirror);
    }
    
    protected boolean isBaseTypeOfClass(TypeMirror mirror, Class<?> cls) {
        return getBaseType(mirror).toString().equals(cls.getCanonicalName());
    }
    
    protected List<AbstractTypeInfo> convertTypeArguments(TypeMirror mirror) {
        final List<? extends TypeMirror> typeArguments = getTypeArguments(mirror);
        return typeArguments.stream().map(typeConverter::convertType).collect(Collectors.toList());
    }
    
    private List<? extends TypeMirror> getTypeArguments(TypeMirror mirror) {
        return ((DeclaredType) mirror).getTypeArguments();
    }
    
    
}
