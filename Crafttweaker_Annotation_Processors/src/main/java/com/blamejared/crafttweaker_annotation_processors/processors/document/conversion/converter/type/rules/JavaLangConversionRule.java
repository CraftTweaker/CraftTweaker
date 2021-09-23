package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.ExistingTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.PrimitiveTypeInfo;

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
        if(isString(element)) {
            return getStringTypeInfo();
        }
        return getExistingTypeInfo(element);
    }
    
    private AbstractTypeInfo getStringTypeInfo() {
        
        return new PrimitiveTypeInfo("string");
    }
    
    private AbstractTypeInfo getExistingTypeInfo(Element element) {
        
        return new ExistingTypeInfo(element.getSimpleName().toString());
    }
    
    private boolean isString(Element mirror) {
        
        return mirror.getSimpleName().contentEquals(String.class.getSimpleName());
    }
    
}
