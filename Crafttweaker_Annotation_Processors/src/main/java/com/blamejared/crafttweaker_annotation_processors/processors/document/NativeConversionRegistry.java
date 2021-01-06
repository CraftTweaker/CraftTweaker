package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.native_types.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.*;

import javax.lang.model.element.*;
import java.util.*;
import java.util.function.*;

public class NativeConversionRegistry implements IHasPostCreationCall {
    
    private final Map<TypeElement, AbstractTypeInfo> nativeTypeToTypeInfo = new HashMap<>();
    private final DependencyContainer dependencyContainer;
    
    public NativeConversionRegistry(DependencyContainer dependencyContainer) {
        this.dependencyContainer = dependencyContainer;
    }
    
    public void addNativeConversion(TypeElement nativeType, AbstractTypeInfo typeInfo) {
        nativeTypeToTypeInfo.put(nativeType, typeInfo);
    }
    
    public boolean hasNativeTypeInfo(TypeElement nativeType) {
        return nativeTypeToTypeInfo.containsKey(nativeType);
    }
    
    public AbstractTypeInfo getNativeTypeInfo(TypeElement nativeType) {
        return nativeTypeToTypeInfo.get(nativeType);
    }
    
    @Override
    public void afterCreation() {
        addCodedNativeTypes();
    }
    
    private void addCodedNativeTypes() {
        addNativeTypeInformationFrom(StdLibsNativeTypeProvider.class);
        addNativeTypeInformationFrom(JavaLangNativeTypeProvider.class);
        addNativeTypeInformationFrom(ModDependencyTypeProvider.class);
    }
    
    private void addNativeTypeInformationFrom(Class<? extends NativeTypeProvider> providerClass) {
        final NativeTypeProvider provider = dependencyContainer.getInstanceOfClass(providerClass);
        addNativeTypeInformationFrom(provider);
    }
    
    private void addNativeTypeInformationFrom(NativeTypeProvider provider) {
        final Map<TypeElement, AbstractTypeInfo> typeInfos = provider.getTypeInfos();
        nativeTypeToTypeInfo.putAll(typeInfos);
    }
    
    public boolean hasNativeTypeInfoWithName(TypeName name) {
        return tryGetNativeTypeInfoWithName(name).isPresent();
    }
    
    private Predicate<AbstractTypeInfo> typeNameMatches(TypeName name) {
        return typeInfo -> typeInfo instanceof TypePageTypeInfo && ((TypePageTypeInfo) typeInfo).getZenCodeName().equals(name);
    }
    
    public AbstractTypeInfo getNativeTypeInfoWithName(TypeName name) {
        return tryGetNativeTypeInfoWithName(name).orElseThrow(() -> new IllegalArgumentException("Could not find type with name: " + name));
    }
    
    public Optional<AbstractTypeInfo> tryGetNativeTypeInfoWithName(TypeName name) {
        return nativeTypeToTypeInfo.values().stream().filter(typeNameMatches(name)).findFirst();
    }
}
