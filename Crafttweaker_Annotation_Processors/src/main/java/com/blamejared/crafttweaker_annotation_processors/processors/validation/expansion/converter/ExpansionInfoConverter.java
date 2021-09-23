package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.converter;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.KnownTypeRegistry;

import java.util.stream.Stream;

public class ExpansionInfoConverter {
    
    private final KnownTypeRegistry knownTypeRegistry;
    private final NativeTypeConverter nativeTypeConverter;
    private final TypedExpansionConverter typedExpansionConverter;
    private final NamedExpansionConverter namedExpansionConverter;
    
    public ExpansionInfoConverter(KnownTypeRegistry knownTypeRegistry, NativeTypeConverter nativeTypeConverter, TypedExpansionConverter typedExpansionConverter, NamedExpansionConverter namedExpansionConverter) {
        
        this.knownTypeRegistry = knownTypeRegistry;
        this.nativeTypeConverter = nativeTypeConverter;
        this.typedExpansionConverter = typedExpansionConverter;
        this.namedExpansionConverter = namedExpansionConverter;
    }
    
    public Stream<ExpansionInfo> convertToExpansionInfos() {
        
        return Stream.concat(expansionInfosWithType(), namedExpansionInfos());
    }
    
    private Stream<ExpansionInfo> namedExpansionInfos() {
        
        return knownTypeRegistry.getExpansionTypes()
                .stream()
                .map(namedExpansionConverter::convertNamedExpansion);
    }
    
    private Stream<ExpansionInfo> expansionInfosWithType() {
        
        return Stream.concat(nativeRegistrations(), typedExpansion());
    }
    
    private Stream<ExpansionInfo> nativeRegistrations() {
        
        return knownTypeRegistry.getNativeTypes()
                .stream()
                .map(nativeTypeConverter::convertNativeType);
    }
    
    
    private Stream<ExpansionInfo> typedExpansion() {
        
        return knownTypeRegistry.getTypedExpansionTypes()
                .stream()
                .map(typedExpansionConverter::convertTypedExpansion);
    }
    
}
