package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules.generic.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.*;

import javax.lang.model.type.*;
import java.util.*;

public class TypeConverter implements IHasPostCreationCall {
    
    private final NativeConversionRegistry nativeConversionRegistry;
    private final DocumentRegistry registry;
    private final DependencyContainer dependencyContainer;
    private final List<TypeConversionRule> rules = new ArrayList<>();
    
    public TypeConverter(NativeConversionRegistry nativeConversionRegistry, DocumentRegistry registry, DependencyContainer dependencyContainer) {
        this.nativeConversionRegistry = nativeConversionRegistry;
        this.registry = registry;
        this.dependencyContainer = dependencyContainer;
    }
    
    public AbstractTypeInfo convertByName(TypeName name) {
        final Optional<TypePageInfo> pageInfoByName = registry.getPageInfoByName(name);
        if(pageInfoByName.isPresent()) {
            return new TypePageTypeInfo(pageInfoByName.get());
        }
        
        if(hasNativePageInfo(name)) {
            return getNativePageInfo(name);
        }
        
        //Problem: When preparing the ATIs we already convert the comments :thinking:
        throw new UnsupportedOperationException("TODO: " + name.getZenCodeName());
    }
    
    private AbstractTypeInfo getNativePageInfo(TypeName name) {
        return nativeConversionRegistry.getNativeTypeInfoWithName(name);
    }
    
    
    private boolean hasNativePageInfo(TypeName name) {
        return nativeConversionRegistry.hasNativeTypeInfoWithName(name);
    }
    
    public AbstractTypeInfo convertType(TypeMirror typeMirror) {
        return tryConvertType(typeMirror)
                .orElseThrow(() -> new IllegalArgumentException("Could not convert " + typeMirror));
    }
    
    public Optional<AbstractTypeInfo> tryConvertType(TypeMirror typeMirror) {
        return rules.stream().filter(rule -> rule.canConvert(typeMirror)).map(rule -> rule.convert(typeMirror)).filter(Objects::nonNull).findFirst();
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
        addConversionRule(JavaFunctionConversionRule.class);
        addConversionRule(PrimitiveConversionRule.class);
        //addConversionRule(FallbackConversionRule.class);
    }
    
    private void addConversionRule(Class<? extends TypeConversionRule> ruleClass) {
        rules.add(dependencyContainer.getInstanceOfClass(ruleClass));
    }
}
