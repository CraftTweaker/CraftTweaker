package com.blamejared.crafttweaker_annotation_processors.processors.document.native_types;

import com.blamejared.crafttweaker_annotation_processors.processors.document.native_types.dependency_rule.ModDependencyConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.native_types.dependency_rule.NamedTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.native_types.dependency_rule.NativeTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.IHasPostCreationCall;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModDependencyTypeProvider implements NativeTypeProvider, IHasPostCreationCall {
    
    private final DependencyContainer dependencyContainer;
    private final List<ModDependencyConversionRule> conversionRules = new ArrayList<>();
    
    public ModDependencyTypeProvider(DependencyContainer dependencyContainer) {
        
        this.dependencyContainer = dependencyContainer;
    }
    
    @Override
    public Map<TypeElement, AbstractTypeInfo> getTypeInfos() {
        
        final HashMap<TypeElement, AbstractTypeInfo> result = new HashMap<>();
        addInfosFromRules(result);
        return result;
    }
    
    private void addInfosFromRules(HashMap<TypeElement, AbstractTypeInfo> result) {
        
        for(ModDependencyConversionRule conversionRule : conversionRules) {
            final Map<TypeElement, AbstractTypeInfo> allFromRule = conversionRule.getAll();
            result.putAll(allFromRule);
        }
    }
    
    
    @Override
    public void afterCreation() {
        
        addConversionRule(NamedTypeConversionRule.class);
        addConversionRule(NativeTypeConversionRule.class);
    }
    
    private void addConversionRule(Class<? extends ModDependencyConversionRule> ruleClass) {
        
        final ModDependencyConversionRule rule = dependencyContainer.getInstanceOfClass(ruleClass);
        this.conversionRules.add(rule);
    }
    
}
