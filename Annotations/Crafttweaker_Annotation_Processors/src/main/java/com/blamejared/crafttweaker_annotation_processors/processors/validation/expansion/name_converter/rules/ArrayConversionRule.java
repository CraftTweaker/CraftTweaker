package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConverter;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class ArrayConversionRule implements NameConversionRule {
    
    private final Types typeUtils;
    private final NameConverter nameConverter;
    
    public ArrayConversionRule(Types typeUtils, NameConverter nameConverter) {
        
        this.typeUtils = typeUtils;
        this.nameConverter = nameConverter;
    }
    
    @Nullable
    @Override
    public TypeMirror convertZenCodeName(String zenCodeName) {
        
        if(isArrayType(zenCodeName)) {
            return getArrayType(zenCodeName);
        }
        return null;
    }
    
    private boolean isArrayType(String zenCodeName) {
        
        return zenCodeName.endsWith("[]");
    }
    
    private TypeMirror getArrayType(String zenCodeName) {
        
        final TypeMirror componentType = getComponentType(zenCodeName);
        return typeUtils.getArrayType(componentType);
    }
    
    private TypeMirror getComponentType(String zenCodeName) {
        
        final String componentString = zenCodeName.substring(0, zenCodeName.length() - 2);
        return nameConverter.getTypeMirrorByZenCodeName(componentString);
    }
    
    
}
