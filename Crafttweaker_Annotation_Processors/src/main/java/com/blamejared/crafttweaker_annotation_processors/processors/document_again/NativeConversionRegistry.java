package com.blamejared.crafttweaker_annotation_processors.processors.document_again;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.Map;

public class NativeConversionRegistry {
    
    private final Map<TypeElement, AbstractTypeInfo> nativeTypeToTypeInfo = new HashMap<>();
    
    public void addNativeConversion(TypeElement nativeType, AbstractTypeInfo typeInfo) {
        nativeTypeToTypeInfo.put(nativeType, typeInfo);
    }
    
    public boolean hasNativeTypeInfo(TypeElement nativeType) {
        return nativeTypeToTypeInfo.containsKey(nativeType);
    }
    
    public AbstractTypeInfo getNativeTypeInfo(TypeElement nativeType) {
        return nativeTypeToTypeInfo.get(nativeType);
    }
}
