package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.PrimitiveTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class JavaLangConversionRule implements TypeConversionRule {
    
    private final Types typeUtils;
    
    public JavaLangConversionRule(Types typeUtils) {
        this.typeUtils = typeUtils;
    }
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        return mirror.toString().startsWith("java.lang");
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        final Element element = typeUtils.asElement(mirror);
        return new PrimitiveTypeInfo(element.getSimpleName().toString());
    }
}
