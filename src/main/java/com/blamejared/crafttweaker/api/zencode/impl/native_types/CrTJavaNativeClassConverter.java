package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.zencode.impl.registry.*;
import com.blamejared.crafttweaker.impl.native_types.*;
import org.openzen.zencode.java.*;
import org.openzen.zencode.java.module.*;
import org.openzen.zencode.java.module.converters.*;

import java.lang.annotation.*;
import java.lang.reflect.*;

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
            CraftTweakerAPI.logger.trace("Minecraft Type referenced but not registered: " + cls.getCanonicalName());
        }
        
        return super.getNameForScripts(cls);
    }
    
    private NativeTypeRegistry getNativeTypeRegistry() {
        
        return zenClassRegistry.getNativeTypeRegistry();
    }
    
    @Override
    public boolean shouldLoadClass(Class<?> cls) {
        
        if(zenClassRegistry.isBlacklisted(cls)) {
            CraftTweakerAPI.logInfo("Not loading class because of blacklist: " + cls.getCanonicalName());
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
