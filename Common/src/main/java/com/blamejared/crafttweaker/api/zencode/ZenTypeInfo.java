package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;

public record ZenTypeInfo(String targetName, TypeKind kind) {
    
    public enum TypeKind {
        CLASS,
        EXPANSION
    }
    
    public static ZenTypeInfo from(final NativeTypeInfo nativeInfo) {
        
        return new ZenTypeInfo(nativeInfo.name(), TypeKind.EXPANSION);
    }
    
}
