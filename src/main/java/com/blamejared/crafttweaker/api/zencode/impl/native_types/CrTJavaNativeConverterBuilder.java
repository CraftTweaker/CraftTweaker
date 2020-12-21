package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.impl.registry.ZenClassRegistry;
import com.blamejared.crafttweaker.impl.native_types.NativeTypeRegistry;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.*;
import org.openzen.zencode.shared.logging.IZSLogger;

public class CrTJavaNativeConverterBuilder extends JavaNativeConverterBuilder {
    
    @Override
    public JavaNativeClassConverter getClassConverter(JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter) {
        final ZenClassRegistry zenClassRegistry = CraftTweakerRegistry.getZenClassRegistry();
        return new CrTJavaNativeClassConverter(packageInfo, typeConversionContext, typeConverter, headerConverter, memberConverter, zenClassRegistry);
    }
    
    @Override
    protected JavaNativeExpansionConverter getExpansionConverter(JavaNativePackageInfo packageInfo, IZSLogger logger, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter) {
        return new CrTJavaNativeExpansionConverter(typeConverter, logger, packageInfo, memberConverter, typeConversionContext, headerConverter);
    }
    
    @Override
    protected JavaNativeConverter getNativeConverter(JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter, JavaNativeClassConverter classConverter, JavaNativeGlobalConverter globalConverter, JavaNativeExpansionConverter expansionConverter) {
        return new CrTJavaNativeConverter(typeConverter, headerConverter, memberConverter, classConverter, globalConverter, expansionConverter, typeConversionContext);
    }
    
}
