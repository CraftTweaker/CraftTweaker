package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.Objects;

public class NamedTypeConversionRule implements TypeConversionRule {
    
    private final TypeConverter typeConverter;
    private final Types typeUtils;
    
    public NamedTypeConversionRule(TypeConverter typeConverter, Types typeUtils) {
        
        this.typeConverter = typeConverter;
        this.typeUtils = typeUtils;
    }
    
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        
        return getNameAnnotation(mirror) != null;
    }
    
    private ZenCodeType.Name getNameAnnotation(TypeMirror mirror) {
        
        final ZenCodeType.Name annotation = mirror.getAnnotation(ZenCodeType.Name.class);
        if(annotation != null) {
            return annotation;
        }
        
        final Element typeElement = typeUtils.asElement(mirror);
        if(typeElement == null) {
            return null;
        } else {
            return typeElement.getAnnotation(ZenCodeType.Name.class);
        }
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        
        final TypeName name = convertTypeName(mirror);
        return typeConverter.convertByName(name);
    }
    
    private TypeName convertTypeName(TypeMirror mirror) {
        
        final ZenCodeType.Name annotation = getNameAnnotation(mirror);
        return new TypeName(Objects.requireNonNull(annotation).value());
    }
    
}
