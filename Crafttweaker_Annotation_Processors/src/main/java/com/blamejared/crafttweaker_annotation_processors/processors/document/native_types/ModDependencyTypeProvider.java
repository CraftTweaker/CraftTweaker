package com.blamejared.crafttweaker_annotation_processors.processors.document.native_types;

import com.blamejared.crafttweaker_annotation_processors.processors.document.dependencies.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.native_types.dependency_rule.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.*;

import javax.lang.model.element.*;
import java.util.*;

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
