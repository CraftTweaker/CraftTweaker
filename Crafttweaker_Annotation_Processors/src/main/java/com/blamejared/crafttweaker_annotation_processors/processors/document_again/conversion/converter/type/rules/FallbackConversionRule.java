package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.PrimitiveTypeInfo;

import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * @deprecated Only used to catch what other conversionRules are missing
 */
@Deprecated
public class FallbackConversionRule implements TypeConversionRule {
    
    private final Messager messager;
    
    public FallbackConversionRule(Messager messager) {
        this.messager = messager;
    }
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        return true;
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        messager.printMessage(Diagnostic.Kind.WARNING, "Unsupported type found: " + mirror);
        return new PrimitiveTypeInfo(mirror.toString());
    }
}
