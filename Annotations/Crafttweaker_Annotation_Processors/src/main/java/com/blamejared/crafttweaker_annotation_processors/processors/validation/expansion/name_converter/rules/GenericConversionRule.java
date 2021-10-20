package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConverter;

import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericConversionRule implements NameConversionRule {
    
    private final Pattern pattern = Pattern.compile("([^<]+)<([^>]+)>");
    
    private final Types typeUtils;
    private final NameConverter nameConverter;
    
    public GenericConversionRule(Types typeUtils, NameConverter nameConverter) {
        
        this.typeUtils = typeUtils;
        this.nameConverter = nameConverter;
    }
    
    @Nullable
    @Override
    public TypeMirror convertZenCodeName(String zenCodeName) {
        
        if(isGeneric(zenCodeName)) {
            return getGeneric(zenCodeName);
        }
        return null;
    }
    
    private boolean isGeneric(String zenCodeName) {
        
        return pattern.matcher(zenCodeName).matches();
    }
    
    private TypeMirror getGeneric(String zenCodeName) {
        
        final Matcher matcher = pattern.matcher(zenCodeName);
        if(!matcher.matches()) {
            throw new IllegalStateException("It matched before but not now? " + zenCodeName);
        }
        final MatchResult matchResult = matcher.toMatchResult();
        
        return getFromMatchResult(matchResult);
    }
    
    private TypeMirror getFromMatchResult(MatchResult matchResult) {
        
        final TypeElement baseType = getBaseType(matchResult);
        final TypeMirror[] arguments = getTypeArguments(matchResult);
        
        return typeUtils.getDeclaredType(baseType, arguments);
    }
    
    private TypeElement getBaseType(MatchResult matchResult) {
        
        final String baseTypeZenCodeName = matchResult.group(1).trim();
        final TypeMirror typeMirrorByZenCodeName = nameConverter.getTypeMirrorByZenCodeName(baseTypeZenCodeName);
        return (TypeElement) typeUtils.asElement(typeMirrorByZenCodeName);
    }
    
    private TypeMirror[] getTypeArguments(MatchResult matchResult) {
        
        final String[] split = matchResult.group(2).split(",\\s*");
        return Arrays.stream(split)
                .map(nameConverter::getTypeMirrorByZenCodeName)
                .toArray(TypeMirror[]::new);
    }
    
}
