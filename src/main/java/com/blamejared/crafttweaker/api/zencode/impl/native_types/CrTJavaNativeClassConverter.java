package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.impl.native_types.NativeTypeRegistry;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.*;

public class CrTJavaNativeClassConverter extends JavaNativeClassConverter {
    
    private final NativeTypeRegistry nativeTypeRegistry;
    
    public CrTJavaNativeClassConverter(JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter) {
        super(typeConverter, memberConverter, packageInfo, typeConversionContext, headerConverter);
        this.nativeTypeRegistry = CraftTweakerRegistry.getNativeTypeRegistry();
    }
    
    @Override
    public String getNameForScripts(Class<?> cls) {
        if(nativeTypeRegistry.hasInfoFor(cls)) {
            return nativeTypeRegistry.getCrTNameFor(cls);
        }
        
        return super.getNameForScripts(cls);
    }
}
