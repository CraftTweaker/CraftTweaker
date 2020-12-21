package com.blamejared.crafttweaker_annotation_processors.processors.document_again;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.IHasPostCreationCall;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.native_types.JavaLangNativeTypeProvider;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.native_types.NativeTypeProvider;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.native_types.StdLibsNativeTypeProvider;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.Map;

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
    }
    
    private void addNativeTypeInformationFrom(Class<? extends NativeTypeProvider> providerClass) {
        final NativeTypeProvider provider = dependencyContainer.getInstanceOfClass(providerClass);
        addNativeTypeInformationFrom(provider);
    }
    
    private void addNativeTypeInformationFrom(NativeTypeProvider provider) {
        final Map<TypeElement, AbstractTypeInfo> typeInfos = provider.getTypeInfos();
        nativeTypeToTypeInfo.putAll(typeInfos);
    }
}
