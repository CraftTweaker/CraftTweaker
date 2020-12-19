package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker_annotations.annotations.NativeExpansion;
import com.blamejared.crafttweaker.impl.native_types.NativeTypeRegistry;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.*;
import org.openzen.zencode.shared.logging.IZSLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class CrTJavaNativeExpansionConverter extends JavaNativeExpansionConverter {
    
    private final NativeTypeRegistry nativeTypeRegistry;
    
    public CrTJavaNativeExpansionConverter(JavaNativeTypeConverter typeConverter, IZSLogger logger, JavaNativePackageInfo packageInfo, JavaNativeMemberConverter memberConverter, JavaNativeTypeConversionContext typeConversionContext, JavaNativeHeaderConverter headerConverter, NativeTypeRegistry nativeTypeRegistry) {
        super(typeConverter, logger, packageInfo, memberConverter, typeConversionContext, headerConverter);
        this.nativeTypeRegistry = nativeTypeRegistry;
    }
    
    @Override
    protected <T extends Annotation> T getMethodAnnotation(Method method, Class<T> annotationClass) {
        return super.getMethodAnnotation(method, annotationClass);
    }
    
    @Override
    protected String getExpandedName(Class<?> cls) {
        if(cls.isAnnotationPresent(NativeExpansion.class)) {
            final NativeExpansion annotation = cls.getAnnotation(NativeExpansion.class);
            return nativeTypeRegistry.getCrTNameFor(annotation.value());
        }
        return super.getExpandedName(cls);
    }
    
    @Override
    protected boolean doesClassNotHaveAnnotation(Class<?> cls) {
        return !cls.isAnnotationPresent(NativeExpansion.class) && super.doesClassNotHaveAnnotation(cls);
    }
}
