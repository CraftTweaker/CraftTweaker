package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConverter;

import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapConversionRule implements NameConversionRule {
    
    private static final Pattern pattern = Pattern.compile("([^\\[]+)\\[([^]]+)]");
    
    private final Elements elementUtils;
    private final Types typeUtils;
    private final NameConverter nameConverter;
    
    public MapConversionRule(Elements elementUtils, Types typeUtils, NameConverter nameConverter) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.nameConverter = nameConverter;
    }
    
    @Nullable
    @Override
    public TypeMirror convertZenCodeName(String zenCodeName) {
        if(isMapType(zenCodeName)) {
            return getMapType(zenCodeName);
        }
        return null;
    }
    
    private boolean isMapType(String zenCodeName) {
        return pattern.matcher(zenCodeName).matches();
    }
    
    private TypeMirror getMapType(String zenCodeName) {
        final Matcher matcher = pattern.matcher(zenCodeName);
        if(!matcher.matches()) {
            throw new IllegalStateException("It matched before but not now? " + zenCodeName);
        }
        return getMapTypeFromMatchResult(matcher.toMatchResult());
    }
    
    private TypeMirror getMapTypeFromMatchResult(MatchResult matchResult) {
        final TypeElement baseType = getMapBaseType();
        final TypeMirror keyType = getMapKeyType(matchResult);
        final TypeMirror valueType = getMapValueType(matchResult);
        
        return typeUtils.getDeclaredType(baseType, keyType, valueType);
    }
    
    private TypeMirror getMapKeyType(MatchResult matchResult) {
        final String keyTypeName = matchResult.group(2).trim();
        return nameConverter.getTypeMirrorByZenCodeName(keyTypeName);
    }
    
    private TypeMirror getMapValueType(MatchResult matchResult) {
        final String valueTypeName = matchResult.group(1).trim();
        return nameConverter.getTypeMirrorByZenCodeName(valueTypeName);
    }
    
    private TypeElement getMapBaseType() {
        return elementUtils.getTypeElement(Map.class.getCanonicalName());
    }
    
}
