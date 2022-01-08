package com.blamejared.crafttweaker.api.zencode.impl.native_type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.natives.NativeTypeRegistry;
import com.blamejared.crafttweaker.api.zencode.impl.registry.ZenClassRegistry;
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

class CrTJavaNativeClassConverter extends JavaNativeClassConverter {
    
    private final ZenClassRegistry zenClassRegistry;
    
    public CrTJavaNativeClassConverter(JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter, ZenClassRegistry zenClassRegistry) {
        
        super(typeConverter, memberConverter, packageInfo, typeConversionContext, headerConverter);
        this.zenClassRegistry = zenClassRegistry;
    }
    
    @Override
    public String getNameForScripts(Class<?> cls) {
        
        
        if(getNativeTypeRegistry().hasInfoFor(cls)) {
            return getNativeTypeRegistry().getCrTNameFor(cls);
        }
        
        if(cls.getCanonicalName().startsWith("net.minecraft")) {
            CraftTweakerAPI.LOGGER.trace("Minecraft Type referenced but not registered: {}", cls.getCanonicalName());
        }
        
        return super.getNameForScripts(cls);
    }
    
    private NativeTypeRegistry getNativeTypeRegistry() {
        
        return zenClassRegistry.getNativeTypeRegistry();
    }
    
    @Override
    public boolean shouldLoadClass(Class<?> cls) {
        
        if(zenClassRegistry.isBlacklisted(cls)) {
            CraftTweakerAPI.LOGGER.info("Not loading class because of blacklist: {}", cls.getCanonicalName());
            return false;
        }
        return super.shouldLoadClass(cls);
    }
    
    @Override
    protected ZenCodeType.Constructor getConstructorAnnotation(Constructor<?> constructor) {
        
        return getNativeTypeRegistry().getMethodInfoFor(constructor)
                .flatMap(it -> it.getAnnotation(ZenCodeType.Constructor.class))
                .orElseGet(() -> super.getConstructorAnnotation(constructor));
    }
    
    
    @Override
    protected <T extends Annotation> T getAnnotation(Method method, Class<T> cls) {
        
        return getNativeTypeRegistry().getMethodInfoFor(method)
                .flatMap(it -> it.getAnnotation(cls))
                .orElseGet(() -> super.getAnnotation(method, cls));
    }
    
    
}
