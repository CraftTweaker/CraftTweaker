package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConverter;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;

public class OptionalConversionRule implements NameConversionRule {
    
    private final NameConverter nameConverter;
    
    public OptionalConversionRule(NameConverter nameConverter) {
        this.nameConverter = nameConverter;
    }
    
    @Nullable
    @Override
    public TypeMirror convertZenCodeName(String zenCodeName) {
        if(isOptional(zenCodeName)) {
            return getWithoutOptional(zenCodeName);
        }
        return null;
    }
    
    private TypeMirror getWithoutOptional(String zenCodeName) {
        final String nameWithoutOptional = zenCodeName.substring(0, zenCodeName.length() - 1);
        return nameConverter.getTypeMirrorByZenCodeName(nameWithoutOptional);
    }
    
    private boolean isOptional(String zenCodeName) {
        return zenCodeName.endsWith("?");
    }
}
