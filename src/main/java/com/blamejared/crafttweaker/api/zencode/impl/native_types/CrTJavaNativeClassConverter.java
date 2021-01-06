package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.zencode.impl.registry.ZenClassRegistry;
import com.blamejared.crafttweaker.impl.native_types.CrTNativeTypeInfo;
import com.blamejared.crafttweaker.impl.native_types.NativeTypeRegistry;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;

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
        final Class<?> declaringClass = constructor.getDeclaringClass();
        if(getNativeTypeRegistry().hasInfoFor(declaringClass)) {
            final ZenCodeType.Constructor result = getNativeConstructorAnnotation(constructor);
            if(result != null) {
                return result;
            }
        }
        
        return super.getConstructorAnnotation(constructor);
    }
    
    private ZenCodeType.Constructor getNativeConstructorAnnotation(Constructor<?> constructor) {
        if(isConstructorRegisteredFor(constructor)) {
            return createConstructorAnnotation();
        }
        return null;
    }
    
    private boolean isConstructorRegisteredFor(Constructor<?> constructor) {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final CrTNativeTypeInfo typeInfoFor = getNativeTypeRegistry().getTypeInfoFor(constructor.getDeclaringClass());
        return typeInfoFor.getConstructors()
                .stream()
                .anyMatch(arguments -> Arrays.equals(arguments, parameterTypes));
    }
    
    private ZenCodeType.Constructor createConstructorAnnotation() {
        return new ZenCodeType.Constructor() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return ZenCodeType.Constructor.class;
            }
        };
    }
    
    
}
