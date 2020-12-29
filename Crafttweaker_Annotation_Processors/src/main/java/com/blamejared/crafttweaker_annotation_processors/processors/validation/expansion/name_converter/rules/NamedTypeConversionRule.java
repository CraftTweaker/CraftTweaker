package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.KnownTypeRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConversionRule;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.function.Predicate;

public class NamedTypeConversionRule implements NameConversionRule {
    
    private final KnownTypeRegistry knownTypeRegistry;
    
    public NamedTypeConversionRule(KnownTypeRegistry knownTypeRegistry) {
        this.knownTypeRegistry = knownTypeRegistry;
    }
    
    @Nullable
    @Override
    public TypeMirror convertZenCodeName(String zenCodeName) {
        return knownTypeRegistry.getNamedTypes()
                .stream()
                .filter(nameMatches(zenCodeName))
                .map(Element::asType)
                .findFirst()
                .orElse(null);
    }
    
    private Predicate<TypeElement> nameMatches(String zenCodeName) {
        return typeElement -> typeElement.getAnnotation(ZenCodeType.Name.class)
                .value()
                .equals(zenCodeName);
    }
}
