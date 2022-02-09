package com.blamejared.crafttweaker.api.zencode.impl.native_type;

//import com.blamejared.crafttweaker.api.CraftTweakerRegistry;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeClassConverter;
import org.openzen.zencode.java.module.converters.JavaNativeConverter;
import org.openzen.zencode.java.module.converters.JavaNativeConverterBuilder;
import org.openzen.zencode.java.module.converters.JavaNativeExpansionConverter;
import org.openzen.zencode.java.module.converters.JavaNativeGlobalConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zencode.shared.logging.IZSLogger;

public class CrTJavaNativeConverterBuilder extends JavaNativeConverterBuilder {
    
    public CrTJavaNativeHeaderConverter headerConverter;
    
    @Override
    public JavaNativeClassConverter getClassConverter(JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter) {
        
        return new CrTJavaNativeClassConverter(packageInfo, typeConversionContext, typeConverter, headerConverter, memberConverter, CraftTweakerAPI.getRegistry()
                .getZenClassRegistry());
    }
    
    @Override
    protected JavaNativeExpansionConverter getExpansionConverter(JavaNativePackageInfo packageInfo, IZSLogger logger, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter) {
        
        return new CrTJavaNativeExpansionConverter(typeConverter, logger, packageInfo, memberConverter, typeConversionContext, headerConverter);
    }
    
    @Override
    protected JavaNativeConverter getNativeConverter(JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter, JavaNativeClassConverter classConverter, JavaNativeGlobalConverter globalConverter, JavaNativeExpansionConverter expansionConverter) {
        
        return new CrTJavaNativeConverter(typeConverter, headerConverter, memberConverter, classConverter, globalConverter, expansionConverter, typeConversionContext);
    }
    
    @Override
    protected JavaNativeHeaderConverter getHeaderConverter(JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter) {
        
        return headerConverter = new CrTJavaNativeHeaderConverter(typeConverter, packageInfo, typeConversionContext);
    }
    
}
