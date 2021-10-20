package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.PrimitiveTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class VoidConversionRule implements TypeConversionRule {
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        
        return mirror.getKind() == TypeKind.VOID;
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        
        return new PrimitiveTypeInfo("void");
    }
    
}
