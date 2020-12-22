package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules.generic.GenericTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules.generic.MapConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.IHasPostCreationCall;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.TypePageTypeInfo;

import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TypeConverter implements IHasPostCreationCall {
    
    private final DocumentRegistry registry;
    private final DependencyContainer dependencyContainer;
    private final List<TypeConversionRule> rules = new ArrayList<>();
    
    public TypeConverter(DocumentRegistry registry, DependencyContainer dependencyContainer) {
        this.registry = registry;
        this.dependencyContainer = dependencyContainer;
    }
    
    
    public AbstractTypeInfo convertByName(TypeName name) {
        final Optional<TypePageInfo> pageInfoByName = registry.getPageInfoByName(name);
        if(pageInfoByName.isPresent()) {
            return new TypePageTypeInfo(pageInfoByName.get());
        }
        
        //Problem: When preparing the ATIs we already convert the comments :thinking:
        throw new UnsupportedOperationException("TODO: " + name.getZenCodeName());
    }
    
    public AbstractTypeInfo convertType(TypeMirror typeMirror) {
        return tryConvertType(typeMirror).orElseThrow(() -> new IllegalArgumentException("Could not convert " + typeMirror));
    }
    
    public Optional<AbstractTypeInfo> tryConvertType(TypeMirror typeMirror) {
        return rules.stream()
                .filter(rule -> rule.canConvert(typeMirror))
                .map(rule -> rule.convert(typeMirror))
                .filter(Objects::nonNull)
                .findFirst();
    }
    
    @Override
    public void afterCreation() {
        addConversionRules();
    }
    
    private void addConversionRules() {
        addConversionRule(TypeParameterConversionRule.class);
        addConversionRule(VoidConversionRule.class);
        addConversionRule(MapConversionRule.class);
        addConversionRule(NativeTypeConversionRule.class);
        addConversionRule(GenericTypeConversionRule.class);
        addConversionRule(ArrayConversionRule.class);
        addConversionRule(NamedTypeConversionRule.class);
        addConversionRule(JavaLangConversionRule.class);
        addConversionRule(PrimitiveConversionRule.class);
        addConversionRule(FallbackConversionRule.class);
    }
    
    private void addConversionRule(Class<? extends TypeConversionRule> ruleClass) {
        rules.add(dependencyContainer.getInstanceOfClass(ruleClass));
    }
}
