package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;

public interface TypeConversionRule {
    
    boolean canConvert(TypeMirror mirror);
    
    @Nullable
    AbstractTypeInfo convert(TypeMirror mirror);
}
