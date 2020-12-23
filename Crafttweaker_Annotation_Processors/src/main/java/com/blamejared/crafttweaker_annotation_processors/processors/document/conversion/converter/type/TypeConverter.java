package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type;

import com.blamejared.crafttweaker_annotation_processors.processors.document.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.generic.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.dependencies.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.*;

import javax.annotation.Nonnull;
import javax.lang.model.type.*;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        
        if(isGeneric(name)) {
            return getGeneric(name);
        }
        
        //Problem: When preparing the ATIs we already convert the comments :thinking:
        throw new UnsupportedOperationException("TODO: " + name.getZenCodeName());
    }
    
    private AbstractTypeInfo getGeneric(TypeName name) {
        final Matcher matcher = getGenericMatcher(name);
        if(!matcher.find()) {
            throw new IllegalArgumentException("Could not extract generic types from name: " + name.getZenCodeName());
        }
        final MatchResult matchResult = matcher.toMatchResult();
        final AbstractTypeInfo baseType = getGenericBaseType(matchResult);
        final List<AbstractTypeInfo> parameters = getGenericParameters(matcher);
        return new GenericTypeInfo(baseType, parameters);
    }
    
    private AbstractTypeInfo getGenericBaseType(MatchResult matchResult) {
        final String zenCodeName = matchResult.group(1).trim();
        return convertByName(new TypeName(zenCodeName));
    }
    
    private List<AbstractTypeInfo> getGenericParameters(Matcher matcher) {
        return Arrays.stream(matcher.group(2).split(","))
                .map(String::trim)
                .map(TypeName::new)
                .map(this::convertByName)
                .collect(Collectors.toList());
    }
    
    @Nonnull
    private Matcher getGenericMatcher(TypeName name) {
        final Pattern compile = Pattern.compile("([^<]+)<([^>]+)>");
        final Matcher matcher = compile.matcher(name.getZenCodeName());
        return matcher;
    }
    
    private boolean isGeneric(TypeName name) {
        return name.getZenCodeName().contains("<");
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
        addConversionRule(GenericTypeConversionRule.class);
        addConversionRule(NativeTypeConversionRule.class);
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
