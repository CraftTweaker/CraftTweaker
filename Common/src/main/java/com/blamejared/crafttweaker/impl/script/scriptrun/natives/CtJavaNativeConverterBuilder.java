package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
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

import java.util.Objects;

public final class CtJavaNativeConverterBuilder extends JavaNativeConverterBuilder {
    
    private final IScriptRunInfo runInfo;
    private CtJavaNativeHeaderConverter headerConverter;
    
    public CtJavaNativeConverterBuilder(final IScriptRunInfo info) {
        
        this.runInfo = info;
    }
    
    @Override
    public JavaNativeClassConverter getClassConverter(
            final JavaNativePackageInfo packageInfo,
            final JavaNativeTypeConversionContext typeConversionContext,
            final JavaNativeTypeConverter typeConverter,
            final JavaNativeHeaderConverter headerConverter,
            final JavaNativeMemberConverter memberConverter
    ) {
        
        return new CtJavaNativeClassConverter(
                packageInfo,
                typeConversionContext,
                typeConverter,
                headerConverter,
                memberConverter,
                CraftTweakerAPI.getRegistry().getZenClassRegistry(),
                this.runInfo
        );
    }
    
    @Override
    protected JavaNativeExpansionConverter getExpansionConverter(
            final JavaNativePackageInfo packageInfo,
            final IZSLogger logger,
            final JavaNativeTypeConversionContext typeConversionContext,
            final JavaNativeTypeConverter typeConverter,
            final JavaNativeHeaderConverter headerConverter,
            final JavaNativeMemberConverter memberConverter
    ) {
        
        return new CtJavaNativeExpansionConverter(
                typeConverter,
                logger,
                packageInfo,
                memberConverter,
                typeConversionContext,
                headerConverter
        );
    }
    
    @Override
    protected JavaNativeConverter getNativeConverter(
            final JavaNativeTypeConversionContext typeConversionContext,
            final JavaNativeTypeConverter typeConverter,
            final JavaNativeHeaderConverter headerConverter,
            final JavaNativeMemberConverter memberConverter,
            final JavaNativeClassConverter classConverter,
            final JavaNativeGlobalConverter globalConverter,
            final JavaNativeExpansionConverter expansionConverter
    ) {
        
        return new CtJavaNativeConverter(
                typeConverter,
                headerConverter,
                memberConverter,
                classConverter,
                globalConverter,
                expansionConverter,
                typeConversionContext
        );
    }
    
    @Override
    protected JavaNativeHeaderConverter getHeaderConverter(
            final JavaNativePackageInfo packageInfo,
            final JavaNativeTypeConversionContext typeConversionContext,
            final JavaNativeTypeConverter typeConverter
    ) {
        
        return this.headerConverter = new CtJavaNativeHeaderConverter(typeConverter, packageInfo, typeConversionContext);
    }
    
    public void reinitializeLazyHeaderValues() {
        
        Objects.requireNonNull(this.headerConverter, "Header converter is not yet available")
                .reinitializeAllLazyValues();
    }
    
}
