package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type;

import com.blamejared.crafttweaker_annotation_processors.processors.document.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document.NativeConversionRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.ArrayConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.JavaFunctionConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.JavaLangConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.NamedTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.NativeTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.NullableAnnotatedParameterConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.PrimitiveConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.PrimitiveWrapperParameterConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.TypeParameterConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.VoidConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.generic.GenericTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules.generic.MapConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.GenericTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.TypePageTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.IHasPostCreationCall;

import javax.annotation.Nonnull;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        throw new UnsupportedOperationException("TODO: Unable to convert page: " + name.getZenCodeName() + " Make sure it has an @Document annotation!");
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
        
        // TODO
        return tryConvertType(typeMirror).orElse(new AbstractTypeInfo() {
            @Override
            public String getDisplayName() {
                
                return "invalid";
            }
            
            @Override
            public String getClickableMarkdown() {
                
                return "**invalid**";
            }
        });
        //                .orElseThrow(() -> new IllegalArgumentException("Could not convert " + typeMirror));
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
        
        addConversionRule(PrimitiveWrapperParameterConversionRule.class);
        addConversionRule(NullableAnnotatedParameterConversionRule.class);
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
