package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter;

import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.IHasPostCreationCall;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules.ArrayConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules.GenericConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules.MapConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules.NamedTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules.NativeTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules.OptionalConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules.PrimitiveConversionRule;

import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NameConverter implements IHasPostCreationCall {
    
    private final List<NameConversionRule> rules = new ArrayList<>();
    private final DependencyContainer container;
    
    public NameConverter(DependencyContainer container) {
        
        this.container = container;
    }
    
    public TypeMirror getTypeMirrorByZenCodeName(String zenCodeName) {
        
        return rules.stream()
                .map(rule -> rule.convertZenCodeName(zenCodeName))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not get Type for zenCode Name: " + zenCodeName));
    }
    
    @Override
    public void afterCreation() {
        
        addRule(PrimitiveConversionRule.class);
        addRule(ArrayConversionRule.class);
        addRule(OptionalConversionRule.class);
        addRule(GenericConversionRule.class);
        addRule(MapConversionRule.class);
        addRule(NamedTypeConversionRule.class);
        addRule(NativeTypeConversionRule.class);
    }
    
    private void addRule(Class<? extends NameConversionRule> ruleClass) {
        
        final NameConversionRule rule = container.getInstanceOfClass(ruleClass);
        this.rules.add(rule);
    }
    
}
