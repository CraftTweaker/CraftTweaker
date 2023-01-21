package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.natives.INativeTypeRegistry;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeClassConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

final class CtJavaNativeClassConverter extends JavaNativeClassConverter {
    
    private final IZenClassRegistry zenClassRegistry;
    private final IScriptRunInfo info;
    
    CtJavaNativeClassConverter(
            final JavaNativePackageInfo packageInfo,
            final JavaNativeTypeConversionContext typeConversionContext,
            final JavaNativeTypeConverter typeConverter,
            final JavaNativeHeaderConverter headerConverter,
            final JavaNativeMemberConverter memberConverter,
            final IZenClassRegistry zenClassRegistry,
            final IScriptRunInfo info
    ) {
        
        super(typeConverter, memberConverter, packageInfo, typeConversionContext, headerConverter);
        this.zenClassRegistry = zenClassRegistry;
        this.info = info;
    }
    
    @Override
    public String getNameForScripts(final Class<?> cls) {
        
        return this.getNativeTypeRegistry()
                .getZenNameFor(cls)
                .orElseGet(() -> {
                    if(cls.getCanonicalName().startsWith("net.minecraft")) {
                        CommonLoggers.zenCode()
                                .trace("Minecraft type referenced but not registered: {}", cls.getCanonicalName());
                    }
                    return super.getNameForScripts(cls);
                });
    }
    
    @Override
    public boolean shouldLoadClass(final Class<?> cls) {
        
        if(this.zenClassRegistry.isBlacklisted(cls)) {
            CommonLoggers.zenCode().info("Not loading class because of blacklist: {}", cls.getCanonicalName());
            return false;
        }
        return super.shouldLoadClass(cls);
    }
    
    @Override
    protected ZenCodeType.Constructor getConstructorAnnotation(final Constructor<?> constructor) {
        
        return this.getNativeTypeRegistry()
                .getExecutableReferenceInfoFor(constructor)
                .flatMap(it -> it.getAnnotation(ZenCodeType.Constructor.class))
                .orElseGet(() -> super.getConstructorAnnotation(constructor));
    }
    
    
    @Override
    protected <T extends Annotation> T getAnnotation(final Method method, final Class<T> cls) {
        
        return this.getNativeTypeRegistry()
                .getExecutableReferenceInfoFor(method)
                .flatMap(it -> it.getAnnotation(cls))
                .orElseGet(() -> super.getAnnotation(method, cls));
    }
    
    private INativeTypeRegistry getNativeTypeRegistry() {
        
        return this.zenClassRegistry.getNativeTypeRegistry(this.info.loader());
    }
    
}
