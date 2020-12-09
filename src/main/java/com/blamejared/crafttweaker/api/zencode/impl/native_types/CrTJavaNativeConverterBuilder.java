package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.*;

public class CrTJavaNativeConverterBuilder extends JavaNativeConverterBuilder {
    
    @Override
    public JavaNativeClassConverter getClassConverter(JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter) {
        return new CrTJavaNativeClassConverter(packageInfo, typeConversionContext, typeConverter, headerConverter, memberConverter);
    }
}
