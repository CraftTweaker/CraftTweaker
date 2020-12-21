package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.PrimitiveTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;

public class JavaLangConversionRule implements TypeConversionRule {
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        return mirror.toString().startsWith("java.lang");
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        return new PrimitiveTypeInfo(mirror.toString());
    }
}
