package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.NullableTypeInfo;
import com.sun.tools.javac.code.Type;
import org.jetbrains.annotations.Nullable;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class NullableAnnotatedParameterConversionRule implements TypeConversionRule {
    
    private final TypeConverter converter;
    
    public NullableAnnotatedParameterConversionRule(final TypeConverter converter) {
        
        this.converter = converter;
    }
    
    @Override
    public boolean canConvert(final TypeMirror mirror) {
        
        return mirror.getAnnotationMirrors().stream().anyMatch(this::isNullableAnnotation);
    }
    
    private boolean isNullableAnnotation(final AnnotationMirror mirror) {
        
        final DeclaredType type = mirror.getAnnotationType();
        final Element element = type.asElement();
        final Element enclosingElement = element.getEnclosingElement();
        final Name elementName = element.getSimpleName();
        final Name enclosingElementName = enclosingElement.getSimpleName();
        return elementName.contentEquals("Nullable") && enclosingElementName.contentEquals("ZenCodeType");
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(final TypeMirror mirror) {
    
        if(!(mirror instanceof Type.AnnotatedType)) {
            return null;
        }
        final Element underlyingType = ((Type.AnnotatedType) mirror).asElement();
        final AbstractTypeInfo typeInfo = this.converter.convertType(underlyingType.asType());
        return new NullableTypeInfo(typeInfo);
    }
    
}
