package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.*;
import org.openzen.zencode.shared.logging.IZSLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class CrTJavaNativeExpansionConverter extends JavaNativeExpansionConverter {
    
    
    public CrTJavaNativeExpansionConverter(JavaNativeTypeConverter typeConverter, IZSLogger logger, JavaNativePackageInfo packageInfo, JavaNativeMemberConverter memberConverter, JavaNativeTypeConversionContext typeConversionContext, JavaNativeHeaderConverter headerConverter) {
        super(typeConverter, logger, packageInfo, memberConverter, typeConversionContext, headerConverter);
    }
    
    @Override
    protected <T extends Annotation> T getMethodAnnotation(Method method, Class<T> annotationClass) {
        return super.getMethodAnnotation(method, annotationClass);
    }
    
    @Override
    protected String getExpandedName(Class<?> cls) {
        if(cls.isAnnotationPresent(NativeTypeRegistration.class)) {
            final NativeTypeRegistration annotation = cls.getAnnotation(NativeTypeRegistration.class);
            return annotation.zenCodeName();
        }
        return super.getExpandedName(cls);
    }
    
    @Override
    protected boolean doesClassNotHaveAnnotation(Class<?> cls) {
        return !cls.isAnnotationPresent(NativeTypeRegistration.class) && super.doesClassNotHaveAnnotation(cls);
    }
}
